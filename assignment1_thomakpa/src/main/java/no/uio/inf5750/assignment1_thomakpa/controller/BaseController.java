package no.uio.inf5750.assignment1_thomakpa.controller;

import no.uio.inf5750.assignment1_thomakpa.dao.MessageDao;

import no.uio.inf5750.assignment1_thomakpa.model.Message;

 

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestParam;

 

@Controller
@RequestMapping("/")
public class BaseController {

	 @RequestMapping(value="/welcome/{name}", method = RequestMethod.GET)
     public String welcomeName(@PathVariable String name, ModelMap model) {

             model.addAttribute("message", "Maven Web Project + Spring 4 MVC - " + name);
             return "index";

     }
     
     @RequestMapping(value="/hello/{name}", method = RequestMethod.GET)
     public String helloWorld(@PathVariable String name, ModelMap model) {

  	   model.addAttribute("message", " hello " + name);
             return "hello";

     }

        @Autowired
        private MessageDao messageDao;

        /*

         * Intro page. No database interaction.

         */

        @RequestMapping(value="/", method = RequestMethod.GET)

        public String welcome(ModelMap model) {
                model.addAttribute("message", "Leave a message using the form");             
                //Spring uses InternalResourceViewResolver and return back index.jsp
                return "index";
        }

 

        /*

         * Saves a message in the database

         */

        @RequestMapping(value="/send", method = RequestMethod.GET)

        public String send(@RequestParam(value="content") String content, ModelMap model) {

 

                Message msg = new Message();

                msg.setContent(content);

                int id = messageDao.save(msg);

                model.addAttribute("message", "Message id of stored message=" + id);

                return "index";

 

        }

        

        /*

         * Prints the last message entered

         */

        @RequestMapping(value="/read", method = RequestMethod.GET)

        public String read(ModelMap model) {

 

                Message message = messageDao.getLast();

                if (message != null) {

                        model.addAttribute("message", "The last message was: "+message.getContent());

                }

                else {

                        model.addAttribute("message", "No message found");                        

                }

 

                //Spring uses InternalResourceViewResolver and return back index.jsp

                return "index";

        }

        

        /*

         * Prints the message with a given id

         */

        @RequestMapping(value="/read/{id}", method = RequestMethod.GET)

        public String readId(@PathVariable int id, ModelMap model) {

 

                Message message = messageDao.get(id);

                if (message != null) {

                        model.addAttribute("message", "Message number "+id+" was: "+message.getContent());

                }

                else {

                        model.addAttribute("message", "No message found");                        

                }

 

                //Spring uses InternalResourceViewResolver and return back index.jsp

                return "index";

        }

        

}