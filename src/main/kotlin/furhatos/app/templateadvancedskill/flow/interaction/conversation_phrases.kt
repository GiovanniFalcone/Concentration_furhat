package furhatos.app.templateadvancedskill.flow.interaction

import furhatos.flow.kotlin.Utterance
import furhatos.flow.kotlin.utterance
import furhatos.flow.kotlin.voice.AzureVoice
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.gestures.Gestures

val phrasesConversation = ConversationPhrases()

class ConversationPhrases {
    val myTellName: Utterance
        get() = utterance {
            random{
                + "Tu, invece, come ti chiami?"
                + "Ora, invece, dimmi il tuo nome"
                + "Qual è il tuo nome?"
                + "Tu, invece, chi sei?"
            }
        }

    val doYouKnowTheGame : Utterance
        get() = utterance {
            random {
                // use prosody tag for emphasis
                + "<prosody rate='95%'>Hai mai giocato a questo gioco?</prosody>"
                + "Conosci questo gioco?"
            }
        }

    val rules : Utterance
        get() = listOf(
            utterance {
                + "è molto semplice"
                + Gestures.Smile
                + delay(100)
                + "Ogni carta ha un immagine."
                + "Se trovi la coppia con la stessa immagine allora lasci le carte scoperte,"
                + "altrimenti queste verranno coperte."
                + delay(100)
                + "L'obiettivo è trovare tutte le coppie."
            },
            utterance {
                + "è un gioco molto semplice!"
                + delay(10)
                + "Ogni turno scoprirai due carte."
                + "Se queste hanno la stessa immagine allora resteranno scoperte. Altrimenti verranno coperte."
                + delay(10)
                + "Il tuo obiettivo è trovare tutte le coppie!"
            }
        ).random()

    val repeatRules: Utterance
        get() = utterance {
            + "Ci sono 24 carte coperte sul tabellone."
            + "Quello che devi fare è trovare le carte con la stessa immagine."
            + delay(10)
            + "Quando ne trovi 2 con la stessa immagine, queste restano scoperte. Altrimenti vengono coperte"
            + delay(10)
            + "Il tuo obiettivo è trovare tutte le coppie!"
        }

    val beforeGameStart : Utterance
        get() = listOf(
            utterance {
                + "Io ti fornirò dei suggerimenti per aiutarti a vincere il più in fretta possibile!"
                // + delay(15)
                // + "Ti fornirò tre tipi di suggerimenti: la riga, la colonna oppure entrambe."
                + delay(10)
                //+ "Qualora non ti fosse chiaro il suggerimento chiedimi pure di ripetere."
                //+ delay(25)
                + "Ovviamente, potrai decidere anche di non seguirli, però prima di fare la tua mossa aspetta che io finisca di parlare."
                + delay(25)
                + "Quindi, fai come meglio credi"
                + delay(15)
                + "Inoltre, ti chiederei di non cliccare troppo velocemente le carte, prenditi il tuo tempo."
                + delay(40)
                + "Adesso ricarica la pagina e giochiamo e facciamo del nostro meglio!"
            },
            utterance {
                + "Ti aiuterò per farti vincere il prima possibile, fornendoti dei suggerimenti."
                + delay(10)
                //+ "Chiedimi pure di ripetere qualora non li capissi."
                // + delay(25)
                + "Puoi anche decidere di non ascoltarmi, però prima di fare la tua mossa aspetta che io finisca di parlare."
                + delay(25)
                + "Quindi gioca come preferisci."
                + delay(15)
                + "Inoltre, ti pregherei di non cliccare troppo velocemente le carte, grazie!"
                + delay(40)
                + "Ora ricarica la pagina e iniziamo a giocare!"
            },
            utterance {
                + "Ti suggerirò dei consigli per farti vincere il prima possibile."
                + delay(10)
                //+ "Se non li capissi chiedimi pure di ripetere."
                //+ delay(25)
                + "Ovviamente puoi giocare come preferisci."
                + delay(25)
                + "Quindi potrai anche non ascoltarmi, però prima di fare la tua mossa aspetta che io finisca di parlare."
                + delay(15)
                + "Inoltre, ti pregherei di non cliccare troppo velocemente le carte, grazie!"
                + delay(40)
                + "Adesso ricarica la pagina e iniziamo a giocare!"
            }
        ).random()

    val endGame: Utterance
        get() = listOf(
            utterance {
                + "Okay, abbiamo finito!"
                + Gestures.BigSmile
                + delay(100)
                + "Spero che tu abbia trovato questa esperienza interessante. Ok, allora ciao!!"
                + delay(10)
                //+ "E' così vero?"
            },

            utterance {
                + "Bene, abbiamo terminato!"
                + Gestures.BigSmile
                + delay(100)
                + "Spero che tu abbia trovato questa esperienza interessante. Ti saluto, ciao!!"
                //+ "Hai trovato utili i miei suggerimenti?"
            },

            utterance {
                + "Ora che la partita è conclusa, possiamo salutarci."
                + Gestures.BigSmile(duration = 2.0)
                + delay(100)
                + "Spero che tu abbia trovato questa esperienza interessante."
                + delay(100)
                + "Bene, ti saluto. Ciao!"
                //+ "Ti sono serviti i miei suggerimenti?"
            }
        ).random()

    fun weHaveToPlayAgain() : Utterance {
        return utterance {
            random {
                + "Riclicca il tasto riprova e giochiamo ancora!"
                + "Non abbiamo finito, quindi ricarica la pagina"
                + "Okay, adesso riclicca il tasto riprova e cerchiamo di fare ancora meglio."
                + "Rigiochiamo e impegniamoci ancora di più."
                + "Giochiamo ancora e cerchiamo di fare ancora meno mosse!"
                + "Dobbiamo ancora giocare. Ricarica la pagina e proviamo a fare di meglio"
                + "Okay, adesso riclicca il tasto riprova e cerchiamo di fare ancora meglio."
            }
            + Gestures.Smile
        }
    }

    fun endedGamePerfectly(): Utterance{
        return utterance {
            random {
                + "Ottimo lavoro"
                + "Ben fatto"
                + "Ottimo lavoro!"
                + "Ben fatto!"
                + "Benissimo!"
                + "Non poteva andare meglio di così"
                + "Ottimo! Allora i miei suggerimenti ti sono stati utili"
            }
            + Gestures.Smile
            +delay(100)
        }
    }

    fun endedGameGood(): Utterance{
        return utterance {
            + Gestures.Thoughtful(duration = 1.5)
            random {
                + "Non male, ma si potrebbe fare di meglio"
                + "Poteva andare meglio"
            }
            +delay(100)
        }
    }

    fun endedGameBad(): Utterance{
        return utterance {
            + Gestures.BrowFrown(duration = 1.5)
            random {
                + "Non è andata particolarmente bene."
                + "Il gioco ha ragione, una foca avrebbe fatto di meglio."
            }
        }
    }
}