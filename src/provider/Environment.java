package provider;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public interface Environment {

    //nom que vous avez donnee à la base de donnée
    public final String environement = "jdbc:postgresql://localhost:5433/JavaApp";
    public final String user = "ouail";
    public final String password = "OUAIL";

    public final String userPath = "userr" ;
    public final String messagePath = "message" ;
    public final String invitedPath = "invited" ;
    public final String friendPath = "friend" ;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final String ACCEPTED_INV = "accepted" ;
    public final String REJECTED_INV = "rejected" ;
    public final String LOADING_INV = "loading" ;

    public final String USERSIDINV = "fromuser" ;
    public final String USERIDINV = "touser";
    public final String USERNAMEDB = "username";
    public final String PASSWORDUSERDB = "password";
    public final String EMAILUSERDB = "email";
    public final String GENDERUSERDB = "sexe";
    public final String NOMUSERDB = "nom";
    public final String PHOTOUSERDB = "photo";
    public final String BIRTHUSERDB = "birth";
    public final String IDUSERDB = "id";

    public final String USERTOMESDB = "userto";
    public final String USERFROMMESDB = "userfrom";
    public final String MESDB = "message";
    public final String MESDATEDB = "dateenvoi";

}
