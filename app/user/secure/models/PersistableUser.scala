package user.secure.models
import securesocial.core.{ Identity, IdentityId }
import net.vz.mongodb.jackson.Id
import net.vz.mongodb.jackson.ObjectId
import util.mongodb.gate.MongoDBGate
import securesocial.core.SocialUser
import securesocial.core.IdentityId
import securesocial.core.AuthenticationMethod
import securesocial.core.PasswordInfo
import scala.beans.BeanProperty


object PersistableUser extends MongoDBGate[PersistableUser, String] {
  def newWithParameters(id: String, provider: String, firstName: String, lastName: String, email: String, password: String): PersistableUser = {
    var instance = new PersistableUser()
    instance.id = id
    instance.provider = provider
    instance.firstName = firstName
    instance.lastName = lastName
    instance.email = email
    instance.password = password
    instance
  }
}


class PersistableUser {
  @BeanProperty @Id  var id: String = null
  @BeanProperty var provider: String = null
  @BeanProperty var firstName: String = null
  @BeanProperty var lastName: String = null
  @BeanProperty var email: String = null
  @BeanProperty var password: String = null
}