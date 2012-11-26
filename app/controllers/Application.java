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
  
/*public static Result index() {
	  return redirect(routes.Application.index());
}*/

public static Result index() {
    return ok(index.render(loginForm));
  }
  
public static Result messages() {
	
			return ok(views.html.messages.render(Message.findAll(), messageForm));	
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
                routes.Application.messages()
            );
        }
    }


}
