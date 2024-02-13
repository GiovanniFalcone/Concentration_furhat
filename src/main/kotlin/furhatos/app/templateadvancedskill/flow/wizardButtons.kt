package furhatos.app.templateadvancedskill.flow

import furhat.libraries.standard.GesturesLib
import furhatos.app.templateadvancedskill.flow.main.*
import furhatos.app.templateadvancedskill.setting.*
import furhatos.flow.kotlin.Color
import furhatos.flow.kotlin.Section
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.partialState

/** Universal button ta always be present **/
val UniversalWizardButtons = partialState {
    onButton("restart", color = Color.Red, section = Section.LEFT) {
        goto(Init)
    }
    onButton("Stop speaking", color = Color.Red, section = Section.LEFT) {
        furhat.stopSpeaking()
    }
}

/** Buttons to speed up testing **/
val TestButtons = partialState {
    onButton("Idle", color = Color.Blue, section = Section.RIGHT) {
        goto(Idle)
    }
    onButton("WaitingForEngagedUser", color = Color.Blue, section = Section.RIGHT) {
        goto(WaitingToStart)
    }
    onButton("Active", color = Color.Blue, section = Section.RIGHT) {
        goto(Active)
    }
    onButton("Greeting", color = Color.Blue, section = Section.RIGHT) {
        goto(Greeting)
    }
    onButton("Nap", color = Color.Blue, section = Section.RIGHT) {
        goto(Nap)
    }
    onButton("DeepSleep", color = Color.Blue, section = Section.RIGHT) {
        goto(DeepSleep)
    }
    onButton("Sleep", color = Color.Blue, section = Section.RIGHT){
        goto(Sleeping)
    }
    onButton("MyWakeUp", color = Color.Yellow, section = Section.RIGHT) {
        furhat.gesture(GesturesLib.PerformWakeUpWithHeadShake, priority = 1)
        delay(1000)
        goto(Active)
    }

    onButton("WakeUp", color = Color.Yellow, section = Section.RIGHT) {
        furhat.wakeUp()
    }
    onButton("WakeUp", color = Color.Yellow, section = Section.RIGHT) {
        furhat.fallASleep()
    }
    onButton("WakeUp", color = Color.Yellow, section = Section.RIGHT) {
        furhat.beIdle()
    }
    onButton("WakeUp", color = Color.Yellow, section = Section.RIGHT) {
        furhat.beActive()
    }

    onButton("set furhat persona", color = Color.Yellow, section = Section.RIGHT) {
        activate(furhatPersona)
    }

    onButton("PlayGame", color = Color.Yellow, section = Section.RIGHT){
        goto(PlayGame)
    }
}