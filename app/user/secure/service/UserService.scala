package user.secure.service

import play.api.Application
import securesocial.core.{ Identity, IdentityId, UserServicePlugin }
import securesocial.core.providers.Token
import securesocial.core.SocialUser
import net.vz.mongodb.jackson.JacksonDBCollection
import play.modules.mongodb.jackson.MongoDB
import net.vz.mongodb.jackson.DBQuery
import net.vz.mongodb.jackson.Id
import net.vz.mongodb.jackson.ObjectId
import user.secure.models.PersistableUser
import user.secure.models.UserConversions._
import user.secure.models.TokenConversions._
import user.secure.models.PersistableToken
import org.joda.time.DateTime

class UserService(application: Application) extends UserServicePlugin(application) {
  /**
   * Finds a user that maches the specified id
   *
   * @param id the user id
   * @return an optional user
   */

  def maybeIfie[T](obj: T) = obj match {
    case null => None
    case _ => Some(obj)
  }

  def find(id: IdentityId): Option[Identity] = maybeIfie(PersistableUser.findById(id.userId))

  /**
   * Finds a user by email and provider id.
   *
   * Note: If you do not plan to use the UsernamePassword provider just provide en empty
   * implementation.
   *
   * @param email - the user email
   * @param providerId - the provider id
   * @return
   */
  def findByEmailAndProvider(email: String, providerId: String): Option[Identity] = {
    val cursor = PersistableUser.find(DBQuery.is("email", email))
    if (cursor.size() == 0) None else maybeIfie(cursor.next())
  }

  /**
   * Saves the user.  This method gets called when a user logs in.
   * This is your chance to save the user information in your backing store.
   * @param user
   */
  def save(user: Identity) = {
    PersistableUser.save(user.asInstanceOf[SocialUser])
    user
  }

  /**
   * Saves a token.  This is needed for users that
   * are creating an account in the system instead of using one in a 3rd party system.
   *
   * Note: If you do not plan to use the UsernamePassword provider just provide en empty
   * implementation
   *
   * @param token The token to save
   */
  def save(token: Token) = PersistableToken.save(token)

  /**
   * Finds a token
   *
   * Note: If you do not plan to use the UsernamePassword provider just provide en empty
   * implementation
   *
   * @param token the token id
   * @return
   */
  def findToken(token: String): Option[Token] = {
    val cursor = PersistableToken.find(DBQuery.is("uuid", token))
    if (cursor.size() == 0) None else maybeIfie(cursor.next())
  }

  /**
   * Deletes a token
   *
   * Note: If you do not plan to use the UsernamePassword provider just provide en empty
   * implementation
   *
   * @param uuid the token id
   */
  def deleteToken(uuid: String) {
    PersistableToken.deleteByUuId(uuid)
  }

  /**
   * Deletes all expired tokens
   *
   * Note: If you do not plan to use the UsernamePassword provider just provide en empty
   * implementation
   *
   */
  def deleteExpiredTokens() {
    PersistableToken.find(DBQuery.greaterThan("exiresAt", DateTime.now()))
  }
}