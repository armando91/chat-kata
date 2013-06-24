package chat.kata

import java.util.Collection;

//import chat.kata.command.SendChatMessageCommand;

class ChatController {

	ChatService chatService;

	static constraints ={
		seq min:0, nullable:true, instanceof:Integer
	}

	def list(Integer seq) {

		if(hasErrors()){
			log.error("Invalid seq: ${errors.getFieldError('seq').rejectedValue}")
			render(status:400,contentType :"text/json"){ error = "Invalid seq parameter" }
			//text:'{"error":"Invalid seq parameter"}'
		}else{

			Collection<ChatMessage> mCollector = new ArrayList<ChatMessage>();
			Integer _last_seq =chatService.collectChatMessages(mCollector, seq);

			render(contentType: "text/json"){
				messages = []
				for(m in mCollector){
					messages.add(nick:m.getNick(),message:m.getMessage())
				}
				last_seq = _last_seq
			}
		}
	}

	def send(){		
		if(!request.JSON){
			render(status:400,contentType :"text/json"){ error = "Invalid body" }
		}else{
			ChatMessage message = new ChatMessage(request.JSON);
			if(!message.validate()){
				if(message.errors.hasFieldErrors("nick")){
					render(status:400,contentType :"text/json"){ error = "Missing nick parameter" }
				}
				if(message.errors.hasFieldErrors("message")){
					render(status:400,contentType :"text/json"){ error = "Missing message parameter" }
				}
			}else{
				chatService.putChatMessage(message)
				render(status:201)
			}
		}
	}

}
