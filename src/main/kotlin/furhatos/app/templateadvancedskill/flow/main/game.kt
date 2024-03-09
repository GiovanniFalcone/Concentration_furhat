package furhatos.app.templateadvancedskill.flow.main

import furhat.libraries.standard.BehaviorLib.AutomaticMovements.randomHeadMovements
import furhatos.app.templateadvancedskill.flow.Parent
import furhatos.app.templateadvancedskill.flow.interaction.phrasesConversation
import furhatos.app.templateadvancedskill.flow.log
import furhatos.app.templateadvancedskill.flow.model.Robot
import furhatos.app.templateadvancedskill.nlu.MyYes
import furhatos.event.actions.ActionGaze
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.records.Location
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.Socket
import java.net.URI

var ip = "192.168.1.56"
var clientPort = 7000

var client: Socket? = null

/** Json message received from Server */
var suggest : String = ""
var row: Int = 0
var column: Int = 0
var cardSuggested : String = ""
var flagToM: String = ""            // flag for Theory of Mind
var flagSuggestion: String = ""      // 1 for suggestion on the first card, 2 otherwise
var robotType: String = ""
var sentence: String = ""

var openCardName : String = ""
var openCardRow: Int = 0
var openCardColumn: Int = 0
var match: Boolean = false
var turn: Int = 0

var centerLocation = Location(0.0, -0.15, 0.5)

val PlayGame: State = state(Parent) {
    include(randomHeadMovements())

    onEntry {
        log.debug("I'm in PlayGame state")

        while (true) {
            try {
                client = Socket(ip, clientPort)

                log.debug("Connection succeeded.")

                while (true) {
                    /** Look the computer */
                    //furhat.attend(centerLocation, gazeMode = ActionGaze.Mode.HEADPOSE, speed = ActionGaze.Speed.XSLOW, slack = 2)

                    /** Get data from Flask server */
                    val buffer = ByteArray(4096)
                    val dataByte = client!!.getInputStream().read(buffer)
                    val dataReceived = String(buffer, 0, dataByte)
                    val jsonData = JSONObject(dataReceived)
                    println(jsonData)

                    if (jsonData.has("action")) {
                        handleActionData(jsonData) // hint provided by agent
                    } else {
                        handleGameData(jsonData)  // what the user has clicked
                    }
                }
            } catch (e: IOException) {
                println("Unable to connect to the server. Please try again in a few seconds.")
                Thread.sleep(5000)
            }
        }
    }
}

fun FlowControlRunner.handleActionData(jsonData: JSONObject) {
    log.debug("handleActionData")

    val list = Robot.getSuggest(jsonData) ?: return
    suggest = list[0].toString()
    cardSuggested = list[1].toString()
    row = list[2].toString().toInt()
    column = list[3].toString().toInt()
    flagToM = list[4].toString()
    flagSuggestion = list[5].toString()
    robotType = list[6].toString()
    sentence = list[7].toString()

    furhat.attend(users.current, gazeMode = ActionGaze.Mode.HEADPOSE, speed = ActionGaze.Speed.XFAST, slack = 1)
    furhat.say(sentence)

    /** After the robot has provided a hint, send to Flask that spoken sentence is finished
     * Used to remove the pop-up on web page after that sentence is finished*/
    if(suggest != "none") {
        println("invio dato al server per rimuovere il pop-up una volta che il robot ha concluso di parlare")
        sendToServerFurhatHasFinishedToSpeech()
    }
}

fun FlowControlRunner.handleGameData(jsonData: JSONObject) {
    openCardName = jsonData.getJSONObject("game").getString("open_card_name")
    openCardRow = jsonData.getJSONObject("game").getJSONArray("position").getInt(0)
    openCardColumn = jsonData.getJSONObject("game").getJSONArray("position").getInt(1)
    turn = jsonData.getJSONObject("game").getInt("turn")
    match = jsonData.getJSONObject("game").getBoolean("match")

    println("handleGameData")

    val pairs = jsonData.getJSONObject("game").getInt("pairs")
    if (pairs == 12) {
        println("Gioco terminato.")
        handleGameEnd()
    }

    val isTurnEven = turn % 2 == 0
    if (match && isTurnEven && suggest != "") {
        random(
            { furhat.gesture(Gestures.Smile) },
            { furhat.gesture(Gestures.Nod) }
        )
    }

    if (!match && isTurnEven) {
        when (suggest) {
            "row", "column" -> {
                random(
                    { furhat.gesture(Gestures.Shake) },
                    { furhat.gesture(Gestures.GazeAway) }
                )
            }
            "card" -> {
                random(
                    { furhat.gesture(Gestures.ExpressAnger) },
                    { furhat.gesture(Gestures.ExpressFear) },
                    { furhat.gesture(Gestures.ExpressSad) }
                )
            }
        }
    }

    suggest = ""
    row = 0
    column = 0
}

/** Send a message in json format to the server*/
fun sendToServerFurhatHasFinishedToSpeech() {
    val jsonObject = """
        {
            "Speech": "Finished"
        }
    """.trimIndent()

    val url = URI.create("http://$ip:5000/").toURL()
    with(url.openConnection() as HttpURLConnection) {
        requestMethod = "POST"
        setRequestProperty("Content-Type", "application/json")
        doOutput = true
        outputStream.bufferedWriter().use { it.write(jsonObject) }

        println("Response code: $responseCode")
    }
}

fun FlowControlRunner.handleGameEnd() {
    furhat.attend(users.current, gazeMode = ActionGaze.Mode.DEADZONE, speed = ActionGaze.Speed.XFAST, slack = 5)

    val message = when {
        turn / 2 < 25 -> phrasesConversation.endedGamePerfectly()
        turn / 2 < 30 -> phrasesConversation.endedGameGood()
        else -> phrasesConversation.endedGameBad()
    }
    furhat.say(message)

    client?.close()
    log.debug("socket closed")

    goto(Goodbye)
}

val Goodbye: State = state(Parent)  {
    onEntry {
        furhat.say(phrasesConversation.endGame) // cambiare in furhat.ask e frasi se si vuole fare la domanda
        goto(Sleeping)
        //furhat.gesture(Listening)
    }

    /** Possibili risposte alle domande di phrasesConversation.endGame.
     * Rimosse in quanto tutti non credevano di poter rispondere al robot */
    onResponse(listOf(Yes(), MyYes())) {
        furhat.say{
            random {
                + "Mi fa molto piacere"
                + "Benissimo"
            }
        }
        furhat.say{
            random {
                + "Allora ciao!"
                + "Bene, adesso dobbiamo salutarci. Ciao!"
                + "è stato divertente, ciao!"
            }
            furhat.gesture(Gestures.BigSmile)
        }
        goto(Active)
    }

    onResponse<No> {
        furhat.say{
            random {
                + "Oh okay"
                + "D'accordo"
                + "Peccato"
                + "Mi spiace che tu la pensi così"
            }
            furhat.gesture(Gestures.ExpressSad)
        }
        furhat.say{
            random {
                + "Allora ciao!"
                + "Bene, adesso dobbiamo salutarci. Ciao!"
            }
            furhat.gesture(Gestures.BigSmile)
        }
        goto(Active)
    }

    onResponse {
        furhat.say{
            + "Non so che dirti"
            + delay(100)
            random {
                + "Allora ciao!"
                + "Bene, adesso dobbiamo salutarci. Ciao!"
            }
            furhat.gesture(Gestures.BigSmile)
        }
        goto(Active)
    }
}