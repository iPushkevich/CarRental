package by.epamtc.pushkevich.service.validator;

public class PasswordValidator implements Validator{
    @Override
    public boolean check(String data) {
        return data.length() >= 6;
    }
}
