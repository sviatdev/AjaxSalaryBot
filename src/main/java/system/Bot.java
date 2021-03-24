package system;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    private static final int defaultWorkingHours = 8;
    private static final double LOW_GRADE = 10200;
    private static final double MIDDLE_GRADE = 10700;
    private static final double HIGH_GRADE = 11700;
    private static final double workAward = 2000;
    private static String grade;
    private static int workingHours;
    private static int workingDays;
    private static double salary;

    private static String calculationForLowGrade(int daysNum, int workingHours) {
        double moneyPerHour = LOW_GRADE / daysNum / defaultWorkingHours;
        double moneyWithoutAward = moneyPerHour * workingHours;
        salary = moneyWithoutAward + workAward;
        String newSalary = String.format("%.2f", salary);
        return "Ставка на LOW грейде: " + LOW_GRADE + "\nВ этом месяце оплата работы в час " + moneyPerHour + " грн" +
                "\nЗарплата за этот месяц без учета премии: " + moneyWithoutAward + " грн" +
                "\nЗарплата с учетом премии: "+ newSalary + " грн";
    }
    private static String calculationForMiddleGrade(int daysNum, int workingHours) {
        double moneyPerHour = MIDDLE_GRADE / daysNum / defaultWorkingHours;
        double moneyWithoutAward = moneyPerHour * workingHours;
        salary = moneyWithoutAward + workAward;
        String newSalary = String.format("%.2f", salary);
        return "Ставка на MIDDLE грейде: " + MIDDLE_GRADE + "\nВ этом месяце оплата работы в час " + moneyPerHour + " грн" +
                "\nЗарплата за этот месяц без учета премии: " + moneyWithoutAward + " грн" +
                "\nЗарплата с учетом премии: "+ newSalary + " грн";
    }
    private static String calculationForHighGrade(int daysNum, int workingHours) {
        double moneyPerHour = HIGH_GRADE / daysNum / defaultWorkingHours;
        double moneyWithoutAward = moneyPerHour * workingHours;
        salary = moneyWithoutAward + workAward;
        String newSalary = String.format("%.2f", salary);
        return "Ставка на HIGH грейде: " + HIGH_GRADE + "\nВ этом месяце оплата работы в час: " + moneyPerHour + " грн" +
                "\nЗарплата за этот месяц без учета премии: " + moneyWithoutAward + " грн" +
                "\nЗарплата с учетом премии: "+ newSalary + " грн";
    }

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());

        //Get grade
        grade = getGrade(update);
        //Get working hours
        workingHours = getWorkingHours(update);
        //Get working days
        workingDays = getWorkingDays(update);
        //Execute bot
        executeTelegramBot(update, sendMessage, grade, workingHours, workingDays);
    }

    private void executeTelegramBot(Update update, SendMessage sendMessage, String grade, int workingHours, int workingDays) {
        if (update.getMessage().getText().equals("/start")) {
            sendMessage.setText("Привет! Этот бот нужен для того, чтобы считать зарплату сотрудникам " +
                    "AJAX Manufacturing. Бот предельно простой, но я надеюсь, что он будет помощником " +
                    "для каждого и станет частью чего-то по-настоящему крутого!\n" +
                    "Пиши /commands чтобы узнать на что способен этот бот\n" +
                    "Берегите себя.\n" +
                    "by sviatdev ");
        }else if(update.getMessage().getText().equals("/bug_report")) {
            sendMessage.setText("Обнаружили баг? Сообщите разработчику о проблеме: @sviatdev");
        }else if(update.getMessage().getText().equals("/commands")){
            sendMessage.setText("Что может этот бот?\n" +
                    "1) Если вы обнаружили баг - /bug_report\n" +
                    "2) Если нужно посчитать зарплату - /c [грейд] [количество рабочих часов] [количество рабочих дней в месяце]\n" +
                    "Пример: /c LOW 264 18");
        } else if (grade.equals("LOW")) {
            sendMessage.setText(calculationForLowGrade(workingDays, workingHours));
        } else if (grade.equals("MIDDLE")) {
            sendMessage.setText(calculationForMiddleGrade(workingDays, workingHours));
        } else if (grade.equals("HIGH")) {
            sendMessage.setText(calculationForHighGrade(workingDays, workingHours));
        } else if (grade.equals("UNKNOWN GRADE")) {
            sendMessage.setText("Введите команду: /commands");
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private int getWorkingHours(Update update) {
        int workingHours = 0;

        String userMessage = update.getMessage().getText();
        StringBuilder sb = new StringBuilder();
        if (userMessage.length() > 9 && (userMessage.contains("LOW")
                || userMessage.contains("MIDDLE")
                || userMessage.contains("HIGH"))) {
            char[] charsOfUserMessage = userMessage.toCharArray();
            for (char c : charsOfUserMessage) {
                if (Character.isDigit(c)) {
                    sb.append(c);
                }
            }
            if(sb.length() == 5) {
                workingHours = Integer.parseInt(sb.substring(0, 3));
                if(sb.length() == 3){

                }
            }

        } else {
            workingHours = 0;
        }
        return workingHours;
    }

    private int getWorkingDays(Update update) {
        int workingDays = 0;

        String userMessage = update.getMessage().getText();
        StringBuilder sb = new StringBuilder();
        if (userMessage.length() > 11 && (userMessage.contains("LOW")
                || userMessage.contains("MIDDLE")
                || userMessage.contains("HIGH"))) {
            char[] charsOfUserMessage = userMessage.toCharArray();
            for (char c : charsOfUserMessage) {
                if (Character.isDigit(c)) {
                    sb.append(c);
                }
            }
            if(sb.length() == 5) {
                workingDays = Integer.parseInt(sb.substring(3, 5));
                if(workingDays <=0 || workingDays >= 31){
                   workingDays = 0;
                }
            }
        } else {
            workingDays = 0;
        }
        return workingDays;
    }

    private String getGrade(Update update) {
        String grade;
        String text = update.getMessage().getText().toUpperCase();
        if (text.startsWith("/") && text.contains("LOW")) {
            grade = update.getMessage().getText().substring(3, 6);
        } else if (text.startsWith("/") && text.contains("MIDDLE")) {
            grade = update.getMessage().getText().substring(3, 9);
        } else if (text.startsWith("/") && text.contains("HIGH")) {
            grade = update.getMessage().getText().substring(3, 7);
        } else {
            grade = "UNKNOWN GRADE";
        }
        String newGrade = grade.toUpperCase();
        return newGrade;
    }

    @Override
    public String getBotUsername() {
        return "AjaxSalaryBot";
    }

    @Override
    public String getBotToken() {
        return "1657609193:AAFr-H5l8jHfCDwPQF9iG5GBLIkBqwdyO40";
    }

}
