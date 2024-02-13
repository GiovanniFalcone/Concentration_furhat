package furhatos.app.templateadvancedskill.nlu

import furhatos.event.Event
import furhatos.nlu.Intent
import furhatos.util.Language

/**
 * Define intents to match a user utterance and assign meaning to what they said.
 * Note that there are more intents available in the Asset Collection in furhat.libraries.standard.NluLib
 **/

class RepeatSuggestion : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Non ho capito",
            "Eh non ho capito",
            "Scusami non ho capito",
            "Non mi è chiaro",
            "Scusa, non ho capito",
            "Scusami ma non ho capito"
        )
    }
}

class MyYes: Intent(){
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Certo",
            "Certamente"
        )
    }
}

class AskToRepeatSuggestion: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Puoi ripetere?",
            "Potresti ripetere, per favore",
            "Potresti ripetere",
            "Scusa, potresti ripetere",
            "Scusami, potresti ripetere"
        )
    }
}

class NiceToMeetYouIntent : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "piacere di conoscerti",
            "felice di vederti",
            "felice di conoscerti",
            "felice di fare la tua conoscenza",
            "mi fa piacere incontrarti"
        )
    }
}

class HelpIntent : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Mi serve aiuto",
            "Ho bisogno di aiuto",
            "Mi serve assistenza",
            "puoi aiutarmi",
            "aiutami",
            "ho bisogno di assistenza"
        )
    }
}

class WakeUp: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "furhat",
            "svegliati",
            "sveglia",
            "hey giochiamo",
            "salve",
            "buongiorno",
            "buonasera"
        )
    }
}

class WhatIsThisIntent : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Cos'è",
            "Cosa dovrei dire",
            "cosa dovrei fare adesso",
            "non so che dire",
            "dovrei dire qualcosa"
        )
    }
}

class LookStraight(val randomMovements: Boolean = true) : Event()
class AttendUsers(val shouldAlterAttentionOnSpeech: Boolean = true) : Event()

open class ConversationalIntent : Intent() {
    // To be used for the sentance "you can ask me $description"
    open val description : String = ""
}

class RequestOptionsIntent : ConversationalIntent() {
    override val description : String = "Possiamo giocare"

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Cosa possiamo fare?")
    }
}