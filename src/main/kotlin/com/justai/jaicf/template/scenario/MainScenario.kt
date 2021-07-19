package com.justai.jaicf.template.scenario

import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.channel.yandexalice.alice
import com.justai.jaicf.channel.yandexalice.model.AliceEvent

val MainScenario = Scenario {


    state("main") {
        activators {
            event(AliceEvent.START)
        }

        action {
            reactions.run {
                say("У меня очень мало времени, но я могу " +
                        "быстренько подсказать время тебе.. ")
                buttons("Подскажи", "Нет уж, спасибо")
                alice?.image(
                    "https://imgur.com/NkkK6iC",
                    "Время в любой точке мира",
                    "Хочешь узнать сколько сейчас времени?"
                )
            }
        }
    }

    state("yes") {
        activators {
            regex(":да давай|да|хочу|давай|с удовольствием|конечно|да интересно" +
                    "подскажи|Да хочу")
        }

        action {
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
                val time= getTimeEuro("Paris")
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
                val time= getTimeEuro("London")
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
                val time= getTimeEuro("Moscow")
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
                val time= getTimeAsia("Vladivostok")
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
                val time= getTimeAsia("Tokyo")
                reactions.say(time)
                reactions.alice?.say("\nХотите узнать время где-нибудь ещё?")
                reactions.buttons("Да, хочу","Нет, спасибо")

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
        reactions.say("Вы тратите моё драгоценное время! Вам интересно " +
                "узнать сколько времени?")
        reactions.buttons("Да", "Нет")
    }
}