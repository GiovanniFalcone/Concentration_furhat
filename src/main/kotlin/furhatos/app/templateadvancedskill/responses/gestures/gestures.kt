package furhatos.app.templateadvancedskill.responses.gestures

import furhatos.gestures.*

/** Define your custom gestures. Note there are more gestures in the Asset Collection furhat.libraries.standard.GesturesLib **/

val hearSpeechGesture = defineGesture("hearSpeechGesture") {
    frame(0.4, persist = true) {
        CharParams.EYEBROW_UP to 1.0
    }
    reset(2.5)
}

// Raise the eyebrows slightly instead to signal interest
val Listening = defineGesture("Listening") {
    frame(0.4, persist = true) {
        Gestures.BrowRaise to 0.8
        Gestures.Smile to 0.6
    }
}

val Normal = defineGesture("Normal") {
    frame(0.4, persist = true) {
        Gestures.BrowRaise to 0.0
        Gestures.Smile to 0.0
    }
    reset(0.5)
}

val reset = defineGesture("reset") {
    frame(0.3) {
        BasicParams.NECK_PAN to 0.0
        BasicParams.NECK_ROLL to 0.0
        BasicParams.NECK_TILT to 0.0
    }
    reset(0.4)
}

fun Wink(strength: Double = 1.0, duration: Double = 1.0) =
    defineGesture("Wink", strength, duration) {
        frame(0.32) {
            BasicParams.EPICANTHIC_FOLD to 0.3
            ARKitParams.EYE_SQUINT_LEFT to 1.0
            ARKitParams.BROW_DOWN_LEFT to 1.0
        }
        reset(0.64)
    }

fun MyDisgust(strength: Double = 1.0, duration: Double = 1.0) =
    defineGesture("ExpressDisgust", strength, duration) {
        frame(0.32) {
            BasicParams.EPICANTHIC_FOLD to 0.3
            ARKitParams.EYE_SQUINT_LEFT to 1.0
            ARKitParams.BROW_DOWN_LEFT to 1.0
        }
        reset(0.64)
    }

fun rollHead(strength: Double = 1.0, duration: Double = 1.0) =
    defineGesture("nod") {
        frame(0.4, duration) {
            BasicParams.NECK_ROLL to strength
        }
        reset(duration + 0.1)
    }