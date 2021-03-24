import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import system.Bot;

public class App {
    public static void main(String[] args) throws TelegramApiException {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();
        botsApi.registerBot(new Bot());
    }
}
