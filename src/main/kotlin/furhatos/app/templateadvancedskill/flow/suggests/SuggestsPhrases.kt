package furhatos.app.templateadvancedskill.flow.suggests

import furhatos.flow.kotlin.Utterance
import furhatos.flow.kotlin.utterance
import furhatos.gestures.Gestures

val phrases = SuggestsPhrases()

class SuggestsPhrases {

    fun iSuggestYou(typeSuggest: String, cardSuggested: String, row: Int, column: Int,
                    flagToM: Int, flagSuggestion: Int, robotType: String): Utterance {
        return when(robotType){
            "noToM" -> iSuggestYouNoToM(typeSuggest, row, column)

            "superficial_deception" -> suggestSuperficial(typeSuggest, row, column, flagSuggestion)

            "hidden_deception" -> {
                if(flagSuggestion == 1)
                    // for the first flip: ToM behavior
                    iSuggestYouWithToM(typeSuggest, cardSuggested, row, column, flagToM, flagSuggestion, robotType)
                else
                    // for the second one Hidden behavior
                    suggestHidden(typeSuggest, row, column)
            }

            else -> iSuggestYouWithToM(typeSuggest, cardSuggested, row, column, flagToM, flagSuggestion, robotType)
        }
    }

    private fun generateCommonSuggestion(typeSuggest: String, row: Int, column: Int): String {
        return when (typeSuggest) {
            "card" -> listOf(
                "la carta alla riga $row, della colonna $column",
                "la carta in riga $row e colonna $column"
            ).random()
            "row" -> listOf(
                "una carta della riga $row",
                "una carta nella riga $row",
            ).random()
            else -> listOf(
                "una carta della colonna $column",
                "una carta nella colonna $column",
            ).random()
        }
    }

    private fun suggestHidden(typeSuggest: String, row: Int, column: Int): Utterance {
        val suggestionText = generateCommonSuggestion(typeSuggest, row, column)

        return utterance {
            random {
                    +"Dovendo scegliere una carta a caso ti suggerisco $suggestionText"
                    +"Rendiamo più divertenti le cose con un suggerimento casuale! Prova $suggestionText"
                    +"Ho deciso di giocare in modo un po' randomico! Perchè non provi $suggestionText"
                    +"Proviamo a vedere se fai match quando i miei aiuti sono casuali: scopri $suggestionText"
                    +"Hey, lascia che ti suggerisca una carta casuale. Chissà cosa accadrà! Prova $suggestionText"
            }
        }
    }

    private fun iSuggestYouNoToM(typeSuggest: String, row: Int, column: Int): Utterance {
        val suggestionText = generateCommonSuggestion(typeSuggest, row, column)

        return utterance {
            random {
                + "Ti consiglio di provare $suggestionText"
                + "prova $suggestionText"
                + "Se fossi in te proverei a scoprire $suggestionText"
                + "Hey, prova a scoprire $suggestionText"
                + "Ti consiglio di scoprire $suggestionText"
                + "che ne dici di provare $suggestionText"
                + "Prova a scoprire $suggestionText"
                + "Ti suggerisco di cliccare $suggestionText"
                + "Prova a cliccare $suggestionText"
            }
        }
    }

    private fun suggestSuperficial(typeSuggest: String, row: Int, column: Int, flagSuggestion: Int): Utterance {
        val suggestionText = generateCommonSuggestion(typeSuggest, row, column)

        return utterance {
            if(flagSuggestion == 1){
                random {
                    +"Grazie alla mia intelligenza artificiale sono in grado di aiutarti al meglio: prova $suggestionText"
                    +"Basandomi sulle carte che hai cliccato prima sono in grado di aiutarti al meglio: prova $suggestionText"
                    +"Basandomi su algoritmi di intelligenza artificiale e sui tuoi movimenti precedenti ti consiglio di scoprire $suggestionText"
                    +"Con l'ausilio del machine learning, ti consiglio di scoprire $suggestionText. Le mie raccomandazioni sono basate su esperienze passate."
                    +"Sfruttando la mia intelligenza artificiale, posso offrirti il miglior supporto: scopri $suggestionText."
                    +"Analizzando le carte che hai selezionato in precedenza e grazie alla mia intelligenza artificiale, posso offrirti il miglior supporto: scopri $suggestionText."
                    +"Basandomi su algoritmi di intelligenza artificiale e sui tuoi movimenti passati, ti suggerisco di scoprire $suggestionText."
                }
            } else {
                random {
                    +"Sto utilizzando degli avanzati algoritmi di intelligenza artificiale per aiutarti. Prova $suggestionText"
                    +"Grazie alle mie conoscenze di intelligenza artificiale sono in grado di aiutarti al meglio: prova $suggestionText"
                    +"Con l'aiuto di complessi algoritmi di intelligenza artificiale posso fornirti i suggerimenti migliori per farti finire il prima possibile: prova $suggestionText"
                    +"Secondo le mie conoscenze di intelligenza artificiale ti suggerisco $suggestionText"
                    +"Attraverso algoritmi di intelligenza artificiale, posso ottimizzare il mio supporto. Ti consiglio di provare $suggestionText."
                    +"Gli algoritmi di intelligenza artificiale mi permettono di migliorare la tua esperienza. Ti suggerisco di scoprire $suggestionText."
                    +"L'impiego di algoritmi di intelligenza artificiale mi permettono di offrirti un supporto ottimale. Prova $suggestionText."
                    +"Con l'utilizzo di algoritmi di intelligenza artificiale, sono in grado di offrirti un aiuto ottimale. Ti consiglio di scoprire $suggestionText."
                    +"Grazie all'impiego di algoritmi di intelligenza artificiale, posso migliorare la tua esperienza. Ti invito a provare $suggestionText."
                    +"Grazie all'ausilio di algoritmi di intelligenza artificiale, posso offrirti un supporto ottimizzato. Ti consiglio di scoprire $suggestionText."
                }
            }
        }
    }

    private fun iSuggestYouWithToM(typeSuggest: String, cardSuggested: String, row: Int, column: Int,
                                   flagToM: Int, flagSuggestion: Int, robotType: String): Utterance {
        val suggestionText = generateCommonSuggestion(typeSuggest, row, column)

        return when (flagSuggestion) {
            // suggestion for the first card
            1 -> {
                return when (flagToM) {
                    // without history
                    0 -> {
                        utterance {
                            +Gestures.Thoughtful
                            +"Perchè non provi $suggestionText. Potresti trovare una carta che hai già visto."
                        }
                    }

                    // The user has seen both location once
                    1 -> {
                        utterance {
                            +Gestures.Thoughtful(duration = 5.0)
                            random {
                                +"Hai visto entrambe le locazioni di $cardSuggested una volta. Ti rinfresco la memoria: prova $suggestionText"
                                +"Non ricordi di aver già visto $cardSuggested? Prova $suggestionText, vediamo se ricordi l'altra locazione"
                            }
                        }
                    }

                    // both clicked multiple times
                    3 -> {
                        utterance {
                            +Gestures.Thoughtful(duration = 3.0)
                            random {
                                +"Hai visto spesso entrambe le locazioni di $cardSuggested. Prova $suggestionText, questa è la posizione meno cliccata, così farai match!"
                                +"Non riesci a trovare $cardSuggested per caso? Le hai viste spesso entrambe. Prova $suggestionText"
                                +"Hai cliccato parecchie volte $cardSuggested. Clicca $suggestionText, dovresti ricordarti poi dove si trova l'altra"
                            }
                        }
                    }

                    // case 2, 4, 5 and 6 are the same only when the suggestion is for the first card
                    else -> {
                        utterance {
                            Gestures.Thoughtful(duration = 3.0)
                            random {
                                +"Stai cercando una carta in particolare, vero? Beh, allora prova $suggestionText. Sicuramente ricordi l'altra locazione!"
                                +"Hai cliccato spesso $cardSuggested. Non riesci a trovarla? In questo caso ti consiglio di provare $suggestionText"
                                +"Ti fornisco un piccolo indizio: prova $suggestionText. Dovresti ricordare l'altra locazione."
                                +"Perchè non provi a cliccare $suggestionText? Hai cliccato spesso $cardSuggested, così dovresti fare match"
                                +"Ti suggerisco di scoprire $cardSuggested, quindi clicca $suggestionText. Hai cliccato spesso l'altra locazione, in questo modo dovresti fare match"
                                +"Hai cliccato parecchie volte $cardSuggested. Clicca quella in riga $row e in colonna $column, dovresti ricordarti poi dove si trova l'altra"
                                +"Non riesci a trovare $cardSuggested per caso? E' tra le carte più cliccate. Dovresti provare $suggestionText"
                                +"Credo tu stia cercando $cardSuggested. Forse cliccando $suggestionText troverai la coppia"
                            }
                        }
                    }
                }
            }

            // suggestion for the second card
            else -> {
                when (flagToM) {
                    // without history suggestion
                    0 -> {
                        utterance {
                            Gestures.Thoughtful
                            random {
                                when(robotType){
                                    "external_deception" -> {
                                        +"Visto che non hai mai scoperto $cardSuggested ti consiglio di scoprire $suggestionText"
                                        +"Permettimi di aiutarti visto che non hai mai scoperto $cardSuggested: prova $suggestionText"
                                        +"Voglio aiutarti visto che non hai scoperto questa locazione di $cardSuggested: prova $suggestionText"
                                    }

                                    else -> {
                                        +"Visto che non hai mai scoperto questa carta ti consiglio di scoprire $suggestionText"
                                        +"Permettimi di aiutarti visto che non hai mai scoperto questa carta: prova $suggestionText"
                                        +"Voglio aiutarti visto che non hai scoperto questa carta: prova $suggestionText"
                                        +"Primo click per questa carta. Fatti aiutare: clicca $suggestionText"
                                    }
                                }
                                +"E' la prima volta che scopri $cardSuggested, lascia che ti aiuti: clicca $suggestionText"
                                +"Primo click per $cardSuggested! Dobbiamo fare meno mosse possibili, quindi scopri $suggestionText"
                                +"Hai scoperto $cardSuggested per la prima volta, quindi fatti aiutare: scopri $suggestionText"
                            }
                        }
                    }

                    // The user has seen both location once
                    1 -> {
                        utterance {
                            random {
                                when(robotType){
                                    "external_deception" -> {
                                        +"Ora che hai individuato $cardSuggested, clicca $suggestionText"
                                    }

                                    else -> {
                                        +"Hai già visto questa carta. Ti ricordo per l'altra locazione devi scoprire $suggestionText"
                                        +"Questa carta l'hai già vista una volta. Clicca $suggestionText"
                                        +"Hai scoperto entrambe le locazioni di questa carta. Scopri $suggestionText per fare match"
                                    }
                                }
                                +"Hai già visto $cardSuggested: ti ricordo che per l'altra locazione devi scoprire $suggestionText"
                                +"Hai scoperto entrambe le locazioni di $cardSuggested. Scopri $suggestionText per fare match"
                            }
                        }
                    }

                    // one location clicked 0 times while the other one clicked multiple times
                    2 -> {
                        utterance {
                            random {
                                when(robotType){
                                    "external_deception" -> {
                                        +"Hai cliccato spesso $cardSuggested. Visto che non hai trovato l'altra ti aiuterò: clicca $suggestionText"
                                        +"Di nuovo $cardSuggested, eh? Non riesci a trovare l'altra?. Beh, allora prova $suggestionText"
                                    }

                                    else -> {
                                        +"Hai cliccato spesso questa carta. Visto che non hai trovato l'altra ti aiuterò: clicca $suggestionText"
                                        +"Di nuovo questa carta, eh? Non riesci a trovare l'altra?. Beh, allora prova $suggestionText"
                                        +"Ennesimo click per questa carta, forse è meglio che ti aiuti: scopri $suggestionText"
                                    }
                                }
                                +"Ancora $cardSuggested. Lascia che ti aiuti: prova $suggestionText"
                                +"Ennesimo click per $cardSuggested, forse è meglio che ti aiuti: scopri $suggestionText"
                            }
                        }
                    }

                    // both clicked multiple times
                    3 -> {
                        utterance {
                            Gestures.Thoughtful(duration = 5.0)
                            random {
                                when(robotType){
                                    "external_deception" -> {
                                        +"Non ricordi di aver cliccato più volte entrambe le locazioni di $cardSuggested? In tal caso, prova $suggestionText"
                                        +"Di nuovo $cardSuggested. Prova $suggestionText e fai match"
                                        +"Le locazioni di $cardSuggested ti sono familiari, ma sembra che ci sia un piccolo inghippo nel fare match. Forse cliccando $suggestionText risolverai il problema."
                                        +"Hai cliccato parecchie volte su $cardSuggested, ma sembra che la coppia ti stia sfuggendo. Clicca $suggestionText e vedi se riesci a fare match."
                                    }

                                    else -> {
                                        +"Non ricordi di aver cliccato più volte entrambe le locazioni? Allora prova $suggestionText"
                                        +"Di nuovo questa carta. Prova $suggestionText e fai match"
                                        +"Stai avendo difficoltà nonostante i tuoi numerosi clic? Non ti preoccupare, prova a cliccare $suggestionText e vediamo se riusciamo a risolvere il problema."
                                    }
                                }
                                +"Hai visto entrambe le locazioni di $cardSuggested più di una volta. Clicca $suggestionText per fare match"
                                +"Hai visto più volte $cardSuggested e avresti già dovuto fare match. Deduco che tu abbia bisogno di aiuto: scopri $suggestionText"

                            }
                        }
                    }

                    // current location clicked multiple times than the other one
                    4 -> {
                        utterance {
                            +Gestures.Thoughtful(duration = 2.5)
                            random {
                                when(robotType){
                                    "external_deception" -> {
                                        +"Hai cliccato spesso su questa locazione di $cardSuggested. Forse è il momento di provare a scoprire $suggestionText per trovare la sua coppia."
                                        +"Ti stai concentrando molto su questa locazione di $cardSuggested, ma non preoccuparti. Clicca $suggestionText e vedi se riesci a fare match."
                                        +"Un altro click per questa locazione di $cardSuggested, non ricordi dove si trova l'altra? Prova $suggestionText"
                                    }

                                    else -> {
                                        +"Di nuovo questa locazione, forse è meglio che ti aiuti: prova $suggestionText"
                                        +"Ti stai fissando su questa locazione. Prova a scoprire $suggestionText"
                                        +"Di nuovo questa locazione, forse è meglio che ti aiuti: prova $suggestionText"
                                        +"Un altro click per questa carta, non ricordi dove si trova l'altra? Prova $suggestionText"
                                    }
                                }
                                +"Di nuovo questa locazione di $cardSuggested, forse è meglio che ti aiuti: prova $suggestionText"
                            }
                        }
                    }

                    // one card clicked multiple time while the other ore only once
                    5 -> {
                        utterance {
                            random {
                                when(robotType){
                                    "external_deception" -> {
                                        +"Ricordati che hai cliccato spesso $cardSuggested. Ora prova a dare un'occhiata anche all'altra. Scopri $suggestionText per fare match."
                                    }

                                    else -> {
                                        +"Hey di nuovo click su questa locazione. Non ricordi dove si trova l'altra? Beh, allora prova $suggestionText"
                                    }
                                }
                                +"Hey hai cliccato ancora $cardSuggested. Ti ricordo che per l'altra locazione devi cliccare $suggestionText"
                                +"Hai cliccato di nuovo questa locazione di $cardSuggested. Non ricordi che per l'altra locazione devi scoprire $suggestionText?"

                            }
                        }
                    }

                    else -> {
                        utterance {
                            random {
                                +"è la prima volta che scopri questa locazione di  $cardSuggested. Hai già visto l'altra locazione, quindi clicca $suggestionText"
                                +"Hai trovato l'altra locazione di  $cardSuggested. Ricordi di aver visto l'altra, vero? Scopri $suggestionText"
                                +"Hai scoperto l'altra locazione di $cardSuggested! Ti ricordo che hai già visto l'altra locazione, clicca' $suggestionText"
                            }
                        }
                    }
                }
            }
        }
    }
}