import java.util.regex.Pattern;

public class Validator {
    public boolean isValidRegistrationNumber(String reg_no) {
        boolean b3 = (Pattern.matches("\\d{4}", reg_no) && !(reg_no.equals("0000")));
        return b3;
    }
}
