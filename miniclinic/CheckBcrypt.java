import org.springframework.security.crypto.bcrypt.BCrypt;

public class CheckBcrypt {
    public static void main(String[] args) {
        String hash = "$2a$10$XhyEgd4qh5TXJa7NkMg3gOqsJxATykAyJERH7ZqTD7eEPVlcmgewm";
        String[] candidates = {"password","12345678","D001","admin","doctor","changeme","password123","00000000","1234","qwerty","secret"};
        for (String c : candidates) {
            System.out.println(c + " -> " + BCrypt.checkpw(c, hash));
        }
    }
}