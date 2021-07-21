package com.justai.jaicf.template.scenario

import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.channel.yandexalice.alice
import com.justai.jaicf.channel.yandexalice.model.AliceEvent
import com.justai.jaicf.context.DefaultActionContext

val MainScenario = Scenario {


    state("main") {
        activators {
            event(AliceEvent.START)
        }

        action {
            reactions.run {
                say(
                    "У меня очень мало времени, но я могу " +
                            "быстренько подсказать время тебе.. "
                )
                buttons("Подскажи", "Нет уж, спасибо")
                alice?.image(
                    "https://imgur.com/NkkK6iC",
                    "Время в любой точке мира",
                    "Хотите узнать когда встаёт или заходит солнце?"
                )
            }
        }
    }

    state("yes") {
        activators {
            regex(
                ":да давай|да|хочу|давай|с удовольствием|конечно|да интересно |" +
                        "подскажи|Да хочу"
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
                val time = getTime(coordinates.lat, coordinates.lon)
                //val time = getTime("59", "30")
                reactions.alice?.say(time)
                reactions.alice?.say("\nХотите узнать время где-нибудь ещё?")
                reactions.buttons("Да, давай", "Нет, спасибо")

            }
        }
        /*        action {
            reactions.run {
                say("В каком городе?")
                buttons("Париж", "Лондон", "Москва", "Владивосток", "Токио")

            }
        }
        state("Paris") {
            activators {
                regex("Париж|в Париже")
            }
            action {
                val coordinates = getCoordinate("Paris")
                val time = getTime(coordinates.lat, coordinates.lon)
                reactions.alice?.say(time)
                reactions.alice?.say("\nХотите узнать время где-нибудь ещё?")
                reactions.buttons("Да, давай","Нет, спасибо")
            }
        }
        state("London") {
            activators {
                regex("Лондон|в Лондоне")
            }
            action {
                val coordinates = getCoordinate("London")
                val time = getTime(coordinates.lat, coordinates.lon)
                reactions.say(time)
                reactions.alice?.say("\nХотите узнать время где-нибудь ещё?")
                reactions.buttons("Да, хочу","Нет, спасибо")

            }
        }
        state("Moscow") {
            activators {
                regex("Москва|Мск|в Москве|Столица|в столице")
            }
            action {
                val coordinates = getCoordinate("Moscow")
                val time = getTime(coordinates.lat, coordinates.lon)
                reactions.say(time)
                reactions.alice?.say("\nХотите узнать время где-нибудь ещё?")
                reactions.buttons("Да, хочу","Нет, спасибо")

            }
        }
        state("Vladivostok") {
            activators {
                regex("Владик|Владивосток|в Владивостоке|на Востоке")
            }
            action {
                val coordinates = getCoordinate("Vladivostok")
                val time = getTime(coordinates.lat, coordinates.lon)
                reactions.say(time)
                reactions.alice?.say("\nХотите узнать время где-нибудь ещё?")
                reactions.buttons("Да, хочу","Нет, спасибо")

            }
        }
        state("Tokyo") {
            activators {
                regex("В Токио|Токио|Япония")
            }
            action {
                val coordinates = getCoordinate("Tokyo")
                val time = getTime(coordinates.lat, coordinates.lon)
                reactions.say(time)
                reactions.alice?.say("\nХотите узнать время где-нибудь ещё?")
                reactions.buttons("Да, хочу","Нет, спасибо")

            }
        }
*/
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
                    "узнать сколько времени?"
        )
        reactions.buttons("Да", "Нет")
    }
}

fun DefaultActionContext.record(message: String, path: String) {
    reactions.say(message)
    reactions.changeState(path)
}
