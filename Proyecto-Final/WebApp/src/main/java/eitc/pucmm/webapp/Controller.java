package eitc.pucmm.webapp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping("/")
    public String login(Model model){
        return "resources/static/login";
    }

    @RequestMapping("/market")
    public String market(Model model){
        return "resources/static/market";
    }
}
