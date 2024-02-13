package furhatos.app.templateadvancedskill.flow.main

import furhat.libraries.standard.GesturesLib
import furhat.libraries.standard.NluLib
import furhatos.app.templateadvancedskill.flow.Parent
import furhatos.app.templateadvancedskill.flow.PowerSaving
import furhatos.app.templateadvancedskill.nlu.AttendUsers
import furhatos.app.templateadvancedskill.nlu.ConversationalIntent
import furhatos.app.templateadvancedskill.nlu.LookStraight
import furhatos.app.templateadvancedskill.nlu.WakeUp
import furhatos.app.templateadvancedskill.responses.gestures.reset
import furhatos.app.templateadvancedskill.setting.*
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.Response
import furhatos.nlu.common.Greeting
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

/**
 * Tip!
 *
 * Use multiple sleep states to set more complex behavior when no users are present.
 *
 * */

/**
 * Light sleep. Listen for wakeup commands when users are present and looking at Furhat
 */
val Nap: State = state {
    include(napHeadMovements)   // prima c'era N maiuscola -> eccezione perchè non trovava la classe
    include(DeepSleepWhenNeglected)

    onEntry {
        furhat.fallASleep()
    }
    onReentry {
        if (furhat.isAttended()) {
            furhat.listen()
        }
    }
    onResponse<Greeting> {
        raise(NluLib.WakeUp()) // Raise another intent to handle pass it on to that trigger handler.
    }
    onResponse<NluLib.WakeUp> { // listen for either a wake-up- or a greeting intent.
        furhat.wakeUp()
        when {
            users.hasAny() -> goto(Active)
            !users.hasAny() -> goto(Idle)
        }
    }
    onUserAttend { // Whenever a user looks at the robot, it will start to listen
        reentry()
    }
    onResponse {
        reentry()
    }
    onNoResponse {
        reentry()
    }
    onNetworkFailed { // override (and silence) default fallback behaviour
        reentry()
    }
    onResponseFailed { // override (and silence) default fallback behaviour
        reentry()
    }
}

/**
 * Deep sleep. Can only wake up from wizard buttons in the web interface.
 */
val DeepSleep: State = state(PowerSaving) {
    onEntry {
        furhat.fallASleep()
        furhat.attend(downMax) // Make head fall even lower, when in "deep sleep".
        furhat.gesture(reset)
        delay(200)
        furhat.setVisibility(false, 3000) // Fade out face to black
    }
    onExit {
        furhat.setVisibility(true, 3000) // Fade in face
    }
}

val Sleeping = state(Parent) {
    onEntry {
        send(LookStraight())
        furhat.gesture(Gestures.CloseEyes, priority = 1)
        furhat.stopSpeaking()
        furhat.listen()
    }

    onReentry {
        furhat.listen()
    }

    onExit {
        //dialogLogger.startSession(cloudToken = "a1671cbb-9b42-4127-b095-5ed12a02ec39")
    }

    onPartialResponse<Greeting>(prefix = true) {
        furhat.gesture(GesturesLib.PerformWakeUpWithHeadShake, priority = 1)
        delay(1000)
        goto(verifyWakeup(it))
    }

    onResponse<Greeting> {
        furhat.gesture(GesturesLib.PerformWakeUpWithHeadShake, priority = 1)
        delay(1000)
        goto(verifyWakeup())
    }

    onPartialResponse<WakeUp>(prefix = true) {
        furhat.gesture(GesturesLib.PerformWakeUpWithHeadShake, priority = 1)
        delay(1000)
        goto(verifyWakeup(it))
    }

    onResponse<WakeUp> {
        furhat.gesture(GesturesLib.PerformWakeUpWithHeadShake, priority = 1)
        delay(1000)
        goto(verifyWakeup())
    }

    onResponse {
        furhat.gesture(Gestures.BrowRaise)
        furhat.listen()
    }

    onNoResponse {
        furhat.listen()
    }
}

fun verifyWakeup(resp: Response<*>? = null) : State = state(Parent) {
    onEntry {
        send(AttendUsers())
        furhat.gesture(reset)
        furhat.gesture(Gestures.OpenEyes, priority = 1)

        // If we have an intent that we have flagged as a conversational intent, we go to active to answer it
        if (resp != null && resp.multiIntent && resp.secondaryIntent is ConversationalIntent) { // is working
            resp.intent = resp.secondaryIntent
            resp.multiIntent = false
            resp.secondaryIntent = null
            goto(Active)
        }
        // If not, we check if the user wanted to address us or if it triggered as a mistake
        else {
            furhat.ask{
                random{
                    + "Stai parlando con me?"
                    + "Vuoi giocare con me?"
                }
                + Gestures.Smile(duration = 2.0)
            }
        }
    }

    onResponse<Yes> {
        goto(Greeting)
    }

    onResponse<No> {
        furhat.say("Oh, okay")
        goto(Sleeping)
    }

    onNoResponse {
        goto(Sleeping)
    }

    onResponse {
        goto(Sleeping)
    }
}
