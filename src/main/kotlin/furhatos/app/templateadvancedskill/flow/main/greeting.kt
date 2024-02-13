package furhatos.app.templateadvancedskill.flow.main

import furhat.libraries.standard.BehaviorLib
import furhatos.app.templateadvancedskill.flow.Parent
import furhatos.app.templateadvancedskill.flow.interaction.phrasesConversation
import furhatos.app.templateadvancedskill.nlu.*
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.gestures.Gestures
import furhatos.nlu.Response
import furhatos.nlu.common.Greeting
import furhatos.util.Language


val Greeting: State = state(Parent) {
    include(BehaviorLib.AutomaticMovements.randomHeadMovements())

    onEntry{
        furhat.say{
            random {
                + "Ciao, io sono Furhat, e sono un robot parlante!"
                + "Ciao, io sono Furhat, un robot parlante! Molto piacere!"
                + "Ciao, piacere di conoscerti, io sono Furhat, un robot parlante!"
                + "Ciao, il mio nome è Furhat, e sono un robot parlante!"
            }
            + delay(50)
            + "Come già saprai, giocheremo insieme al memory game."
            + delay(25)
            + phrasesConversation.beforeGameStart
        }

        goto(PlayGame)
    }
}


/**
 * Example state of a simple flow to greet a user.
 *
 */
// define the state as a function to be able to pass arguments to it
fun GreetUser(response: Response<*>? = null): State = state(Parent) {
    onEntry {
        furhat.ask("come ti chiami")
        //if (response != null) raise(response) // raise any response that was passed on and handle the response here
        //else furhat.listen() // or start a listen to collect a response in this state
    }
    // Handle partial responses where the user said a greeting and something else.
    onPartialResponse<Greeting> {
        furhat.say {
            random {
                +"Ciao!"
                +"Hey!"
            }
        }
        // Raising the secondary intent will cause our triggers to handle the second part of the intent
        // Also raising the response (it) allows for acting on information in the response - e.g. what user spoke
        raise(it, it.secondaryIntent)
    }
    onResponse<Greeting> {
        furhat.say {
            random {
                +"Ciao!"
                +"Hey!"
            }
        }
        goto(Active)
    }

    onResponse<NiceToMeetYouIntent> {
        furhat.say {
            random {
                +"Piacere di conoscerti. "
                +"Piacere mio"
            }
            +Gestures.BigSmile
        }
        goto(Active)
    }
    onResponse {
        furhat.say("piacere di conoscerti ${it.text}")
    }
    onNoResponse {
        goto(Active)
    }

}

/** Run this to test the intents of this state from the run terminal in IntelliJ. **/
fun main(args: Array<String>) {
    println("Type to test the intents of this state. (please ignore the initial error messages)")
    while (true) {
        println("Enter your user response...")
        val utterance = readLine()
        val results = GreetUser(null).getIntentClassifier(lang = Language.ITALIAN).classify(utterance!!)
        if (results.isEmpty()) {
            println("No match")
        } else {
            results.forEach {
                println("Matched ${it.intents} with ${it.conf} confidence")
            }
        }
    }
}