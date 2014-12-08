package user.secure.models

import securesocial.core.AuthenticationMethod
import securesocial.core.IdentityId
import securesocial.core.PasswordInfo
import securesocial.core.SocialUser

import securesocial.core.providers.Token

object TokenConversions {
  implicit def PersistableTokenToToken(token: PersistableToken): Token = 
    new Token ( token.uuid, token.email, token.createdAt, token.exiresAt, token.signUp)
//    var newToken: Token = new Token()
//    newToken.uuid = token.uuid
//    newToken.email = token.email
//    newToken.creationTime = token.createdAt
//    newToken.expirationTime = token.exiresAt
//    newToken.isSignUp = token.isSignUp
//    newToken
  
  implicit def TokenToPersistableToken(token: Token): PersistableToken = PersistableToken.newWithParameters(token.uuid, token.email, token.creationTime, token.expirationTime, token.isSignUp)
}



object UserConversions {
  implicit def PersistableUserToSocialUser(user: PersistableUser): SocialUser =
    new SocialUser(new IdentityId(user.id, user.provider),
      user.firstName, user.lastName, String.format("%s %s", user.firstName, user.lastName),
      Option.apply(user.email), null, new AuthenticationMethod("userPassword"),
      null, null, Some.apply(new PasswordInfo("bcrypt", user.password, null)))

  implicit def SocialUserToPersistableUser(user: SocialUser): PersistableUser =
    PersistableUser.newWithParameters(user.identityId.userId, user.identityId.providerId,
      user.firstName, user.lastName,
      user.email.get, user.passwordInfo.get.password)

}
