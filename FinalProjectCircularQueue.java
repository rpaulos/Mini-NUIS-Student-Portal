package finalprojectcircularqueue;

public class FinalProjectCircularQueue {


    public static void main(String[] args) {

        login LoginFrame = new login();
        LoginFrame.setVisible(true);
        LoginFrame.pack();
        LoginFrame.setLocationRelativeTo(null);
        
    }

    public boolean validateCredentials(String email, String password) {
        String validEmail = "fakeemail@gmail.com";
        String validPassword = "validPassword";
        
        return email.equals(validEmail) && password.equals(validPassword);
    }

}