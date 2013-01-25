package controllers;

import java.util.List;

import models.Message;
import models.UserAccount;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	
	static Form<Message> messageForm = form(Message.class);
	static Form<UserAccount> userForm = form(UserAccount.class);
	static Form<Application.Login> loginForm=form(Login.class);
  
public static Result index() {
    return ok(index.render(loginForm));
  }

public static Result messages() {
UserAccount currentUser = UserAccount.findByNickname(session().get("nickname"));
List<Message> messages = currentUser.getMessages();
return ok(views.html.messages.render(messages, messageForm));
}

public static Result mesmessages() {
	List<Message> nmessages = Message.findAll();
	return ok(mesmessages.render(Message.findAll(), messageForm));	
}		
		

public static Result newMessage() {
	Form<Message> filledForm = messageForm.bindFromRequest();

	if (filledForm.hasErrors()) {
		return badRequest(
				messages.render(Message.findAll(), filledForm)
				);
	}
	else {
	Message msg = filledForm.get();
	UserAccount currentUser = UserAccount.findByNickname(session().get("nickname"));
	Logger.info(currentUser.toString());
	msg.setUser(currentUser);
	Message.create(msg);
	Logger.info(msg.toString());
	return redirect(routes.Application.messages());
	}
}

public static Result deleteMessage(Long id) {
	String identifier = session("nickname")  ;
	UserAccount currentUser ;
	currentUser = UserAccount.findByNickname(identifier);
	            session("nickname", currentUser.getNickname());
	Message.delete(id);
	return redirect(routes.Application.messages()); 
}

public static Result findMessageById(Long id){
	List<Message>m=UserAccount.findById(id).getMessages();
	return ok(messages.render(m, messageForm));	
}
//--creation de comptes

public static Result inscriptions() {
	List<UserAccount> minscriptionss = UserAccount.findAll();
	return ok(inscriptions.render(UserAccount.findAll(), userForm));	
}
public static Result newUser() {
	Form<UserAccount> filledForm = userForm.bindFromRequest();
	if(filledForm.hasErrors()) {
		return badRequest(
				inscriptions.render(UserAccount.findAll(), filledForm)
				);
	} else {
		UserAccount.create(filledForm.get());
		return redirect(routes.Application.login());  
	}
}


//-- Authentication

public static class Login {
    
    public String identifier;
    public String password;
    
    public String validate() {
        if(UserAccount.authenticateMail(identifier, password) == null && UserAccount.authenticateNickname(identifier, password) == null) {
            return "Invalid user or password";
        }
        return null;
    }
    
}
/**
* Login page.
*/
    public static Result login() {
        return ok(
            index.render(form(Login.class))
        );
    }
/**
* Handle login form submission.
*/
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()) {
            return badRequest(index.render(loginForm));
        } else {
String identifier = loginForm.get().identifier ;
UserAccount currentUser ;
if(identifier.contains("@"))
{
currentUser = UserAccount.findByEmail(identifier);
}
else currentUser = UserAccount.findByNickname(identifier);
            session("nickname", currentUser.getNickname());
            return redirect(
            		routes.Application.messages());
        }
    }

    public static Result logout() {
        session().clear();
        flash("success", "Vous êtes déconnécté !!!");
        return redirect(
            routes.Application.login()
        );
    }


}
