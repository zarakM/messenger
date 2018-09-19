package pk.zarsh.messenger
/**
 * Created by HP on 1/21/2018.
 */
class messageC constructor(sender: String, receiver: String, message:String ){
    var sender:String
    var receiver:String
    var message:String
    init {
        this.sender=sender
        this.receiver=receiver
        this.message=message
    }
}