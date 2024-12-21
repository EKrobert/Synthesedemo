package ma.ac.uir.synthesedemo.utils;


import jakarta.servlet.http.HttpSession;
import ma.ac.uir.synthesedemo.entity.Users;

public class SessionUtils {
    public static Users getLoggedInUser(HttpSession session) {
        return (Users) session.getAttribute("user");
    }

    public static void setLoggedInUser(HttpSession session, Users user) {
        session.setAttribute("user", user);
    }

    public static void logout(HttpSession session) {
        session.invalidate();
    }
    // Vérifier si l'utilisateur a un rôle spécifique
    public static boolean hasRole(Users user, int role) {
        return user != null && user.getRole() == role;
    }
}
