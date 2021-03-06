package com.justai.jaicf.template.scenario
import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.channel.yandexalice.alice
import com.justai.jaicf.channel.yandexalice.model.AliceEvent
import com.justai.jaicf.context.DefaultActionContext
import com.justai.jaicf.template.methods.getCoordinate
import com.justai.jaicf.template.methods.getTime

val MainScenario = Scenario {


    state("main") {
        activators {
            event(AliceEvent.START)
        }

        action {
            reactions.say("Хотите узнать, когда встаёт и заходит солнце?")
            reactions.alice?.image("https://i.imgur.com/iVG3EgK.png",
            "Мировое время",
            "Время заката и рассвета в любой точке мира"
            )
            reactions.alice?.buttons("Хочу", "Нет уж, спасибо")

        }
    }

    state("yes") {
        activators {
            regex(
                ":да давай|да|хочу|давай|с удовольствием|конечно|да интересно|" +
                        "подскажи|Да хочу|хочу|очень хочу"
            )
        }
        action {
            record("Какой город вас интересует?", "city")
        }
        state("city") {
            activators {
                catchAll()
            }

            action {

                val coordinates = getCoordinate(request.input)
                val time = getTime(coordinates.lat, coordinates.lon,
                    coordinates.timeZoneName, coordinates.cityName)
                reactions.alice?.say(time)
                reactions.alice?.say("\n")
                reactions.alice?.say("Хотите узнать время где-нибудь ещё?")
                reactions.buttons("Да, давай", "Нет, спасибо")

            }
        }
    }

    state("no") {
        activators {
            regex("нет|не хочу|Нет спасибо|Нет уж спасибо")
        }

        action {
            reactions.say("Тогда прошу меня простить, я спешу!")
            reactions.alice?.endSession()
        }
    }

    fallback {
        reactions.say(
            "Вы тратите моё драгоценное время! Вам интересно " +
                    "узнать время восхода и заката?"
        )
        reactions.buttons("Да", "Нет")
    }
}

fun DefaultActionContext.record(message: String, path: String) {
    reactions.say(message)
    reactions.changeState(path)
}
