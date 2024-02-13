package furhatos.app.templateadvancedskill.flow.model

import org.json.JSONObject

class Robot {
    companion object {
        fun getSuggest(jsonObject: JSONObject): List<Any?>? {
            val suggest = jsonObject.getJSONObject("action").getString("suggestion")
            if(suggest != "none") {
                val row = jsonObject.getJSONObject("action").getJSONArray("position").getInt(0)
                val column = jsonObject.getJSONObject("action").getJSONArray("position").getInt(1)
                var card = jsonObject.getJSONObject("action").getString("card")
                val flagToM = jsonObject.getJSONObject("action").getInt("flagToM")
                val flagSuggestion = jsonObject.getJSONObject("action").getInt("flagSuggestion")
                val robotType = jsonObject.getJSONObject("action").getString("robot_type")

                card = getCardinIta(card)

                return listOf(suggest, card, row, column, flagToM, flagSuggestion, robotType)
            }
            return null
        }

        private fun getCardinIta(cardName: String?): String {
            return when(cardName){
                "panda" -> "panda"
                "pelican" -> "pellicano"
                "tiger" -> "tigre"
                "shark" -> "squalo"
                "koala" -> "koala"
                "flamingo" -> "fenicottero"
                "penguin" -> "pinguino"
                "duck" -> "anatra"
                "goose" -> "oca"
                "walrus" -> "foca"
                "horse" -> "cavallo"
                else -> "uccellino"
            }
        }
    }
}