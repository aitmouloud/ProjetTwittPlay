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
  
public static Result index() {
	  return redirect(routes.Application.index());
}
  
public static Result messages() {
	
			return ok(views.html.messages.render(Message.findAll(), messageForm));	
		}

}
