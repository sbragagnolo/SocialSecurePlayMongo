package user.secure.models

import net.vz.mongodb.jackson.Id
import net.vz.mongodb.jackson.ObjectId
import util.mongodb.gate.MongoDBGate
import org.joda.time.DateTime
import org.joda.time.Duration
import scala.beans.BeanProperty
import securesocial.core.java.Token
import net.vz.mongodb.jackson.DBQuery

object PersistableToken extends MongoDBGate[PersistableToken, String] {
  def newWithParameters(uuid: String, email: String, createdAt: DateTime, exiresAt: DateTime, isSignUp: Boolean): PersistableToken = {
    var instance = new PersistableToken()
    instance.uuid = uuid
    instance.email = email
    instance.createdAt = createdAt
    instance.exiresAt = exiresAt
    instance.signUp = isSignUp
    instance
  }
  def deleteByUuId( uuid: String) = {
    this.models.remove(DBQuery.is("uuid", uuid))
    
  }
    

}

class PersistableToken {
  @BeanProperty @Id @ObjectId  var id :String = null
  @BeanProperty var uuid: String = null
  @BeanProperty var email: String = null
  @BeanProperty var createdAt: DateTime = null
  @BeanProperty var exiresAt: DateTime = null
  @BeanProperty var signUp: Boolean = false

}