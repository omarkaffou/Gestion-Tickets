package emsi.chakir_kaffou.devoir.mvccontrollers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import emsi.chakir_kaffou.devoir.models.Status;
import emsi.chakir_kaffou.devoir.models.User;
import emsi.chakir_kaffou.devoir.services.TicketService;
import emsi.chakir_kaffou.devoir.services.UserService;
import emsi.chakir_kaffou.devoir.utils.Constants;

@Controller
@RequestMapping("dev")
public class DevMvcController {

    @Autowired
    private UserService<User> userService;
    @Autowired
    private TicketService ticketService;
    
    @GetMapping("/{id}/tickets")
    public ModelAndView getTickets(@PathVariable("id") Integer id, Authentication auth) {
        ModelAndView mv = new ModelAndView("tickets");
        mv.addObject("tickets", ticketService.findByDevId(id));
        User user = userService.findByEmail(auth.getName());
        mv.addObject("dev", user.getRole().equals(Constants.getRole("dev")));
        mv.addObject("name", user.getDisplayName());
        return mv;
    }

    @GetMapping("/{id}/status")
    public String getStatus(@PathVariable("id") Integer id, Authentication auth, Model model) {
		model.addAttribute("tickets", ticketService.findByDevId(id));
		model.addAttribute("status", Status.values());

        User user = userService.findByEmail(auth.getName());
        model.addAttribute("dev", user.getRole().equals(Constants.getRole("dev")));
        model.addAttribute("name", user.getDisplayName());
		return "status";
    }

    @PostMapping("/status")
    public String updateStatus(@RequestParam(name = "status", required = true) String status,
            @RequestParam("ticketId") int ticket_id) {
        ticketService.updateStatus(Status.valueOf(status.toUpperCase()), ticket_id);
        return "redirect:/";
    }
}
