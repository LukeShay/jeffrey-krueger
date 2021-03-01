import com.lukeshay.discord.config.Bot
import com.lukeshay.discord.config.Config
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main() {
    val ctx = AnnotationConfigApplicationContext(Config::class.java)
    val jda = ctx.getBean(Bot::class.java).start()

    jda.awaitReady()
}
