package emsi.chakir_kaffou.devoir.mvccontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import emsi.chakir_kaffou.devoir.models.Client;
import emsi.chakir_kaffou.devoir.models.Status;
import emsi.chakir_kaffou.devoir.models.Ticket;
import emsi.chakir_kaffou.devoir.models.Urgence;
import emsi.chakir_kaffou.devoir.models.User;
import emsi.chakir_kaffou.devoir.services.SoftwareService;
import emsi.chakir_kaffou.devoir.services.TicketService;
import emsi.chakir_kaffou.devoir.services.UserService;
import emsi.chakir_kaffou.devoir.utils.Constants;

@Controller
@RequestMapping("client")
public class ClientMvcController {

    @Autowired
    private UserService<Client> userService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private SoftwareService softwareService;
    
    @GetMapping("/{id}/tickets")
    public ModelAndView getTickets(@PathVariable("id") Integer id, Authentication auth) {
        ModelAndView mv = new ModelAndView("tickets");
        mv.addObject("tickets", ticketService.findByClientId(id));
        User user = userService.findByEmail(auth.getName());
        mv.addObject("client", user.getRole().equals(Constants.getRole("client")));
        mv.addObject("name", user.getDisplayName());
        return mv;
    }

    @GetMapping("/tickets/add")
	public String add(Model model, Authentication auth) {
        Ticket ticket = new Ticket();
		model.addAttribute("ticket", ticket);
        model.addAttribute("urgences", Urgence.values());
        model.addAttribute("apps", softwareService.findAll());
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("client", user.getRole().equals(Constants.getRole("client")));
        model.addAttribute("id", user.getId());
        model.addAttribute("name", user.getDisplayName());
		return "addTicket";

	}

    @PostMapping("/tickets/add")
    public String save(Authentication auth, 
        @RequestParam("clientId") int id,
        @RequestParam("description") String description,
        @RequestParam("urgence") Urgence urgence,
        @RequestParam("os") String os,
        @RequestParam("software") int softwareId 
    ) {
        Ticket ticket = new Ticket();
        try {
            ticket.setDescription(description);
            ticket.setUrgence(urgence);
            ticket.setStatus(Status.OPEN);
            ticket.setDev(null);
            ticket.setOs(os);
            ticket.setClient(userService.findById(id));
            ticket.setSoftware(softwareService.findById(softwareId));
            ticketService.add(ticket);
            return "redirect:/client/" + id + "/tickets";
        } catch (Exception e) {
            return "redirect:/client/tickets/add";
        }
	}
}
