class UrlMappings {

	static mappings = {		
		"/"(view:"/index")
		"500"(view:'/error')
		"/api/chat/"(controller: "chat"){
			action = [GET: "list", POST:"send"]

	   }
	}
}
