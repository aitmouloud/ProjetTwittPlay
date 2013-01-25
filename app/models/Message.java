package models;

import java.util.List;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

import play.data.validation.Constraints.Required;
import play.data.validation.Constraints.Min;
import play.db.ebean.Model;

@Entity
public class Message extends Model 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Required
	private String label;

	@Temporal(TemporalType.DATE)
	private Date date = new Date();
	 
	@ManyToOne
	private UserAccount user;

	public Long getId()
	{
		return id ;
	}

	public void setId(Long _id)
	{
		id = _id ;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String _label)
	{
		label = _label ;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public UserAccount getUser()
	{
		return user ;
	}

	public void setUser(UserAccount _user)
	{
		user = _user ;
	}

	public static Finder<Long,Message> find = new Finder(Long.class, Message.class);
	
	public static List<Message> findAll() 
	{
		return find.all();
	}

	public static void create(Message message) 
	{
		message.save();
	}

	public static void delete(Long id) 
	{
		find.ref(id).delete();
	}



	/*@Override
	public String toString() {
		return "Message [id=" + id + ", label=" + label + ", user=" + user
				+ "]";
	}*/
	
	
	
}
