package emsi.chakir_kaffou.devoir.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import emsi.chakir_kaffou.devoir.models.Admin;
import emsi.chakir_kaffou.devoir.models.Software;
import emsi.chakir_kaffou.devoir.models.Ticket;
import emsi.chakir_kaffou.devoir.services.SoftwareService;
import emsi.chakir_kaffou.devoir.services.TicketService;
import emsi.chakir_kaffou.devoir.services.UserService;
import emsi.chakir_kaffou.devoir.utils.Constants;


@RestController
@RequestMapping(Constants.API_PREFIX + "/admins")
public class AdminController {
    
    @Autowired
    private UserService<Admin> userService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private SoftwareService softwareService;

    @GetMapping
    public ResponseEntity<List<Admin>> getAll() {
        return ResponseEntity.ok(userService.findByRole(Constants.getRole("admin")));
    }
    
    @PostMapping
    public ResponseEntity<Admin> add(@RequestBody Admin admin) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(admin));
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getAllTickets(@RequestParam(name = "assigned", required = false) Optional<Boolean> assigned) {
        if (assigned.isPresent()) {
            if (!assigned.get()) {
                return ResponseEntity.ok(ticketService.findAll().stream().filter(ticket -> ticket.getDev() == null).collect(Collectors.toList()));
            }
            if (assigned.get()) {
                return ResponseEntity.ok(ticketService.findAll().stream().filter(ticket -> ticket.getDev() != null).collect(Collectors.toList()));
            }
        }
        return ResponseEntity.ok(ticketService.findAll());
    }

    @PutMapping("/tickets/{ticket_id}/assignTo/{dev_id}")
    public ResponseEntity<Ticket> assignTicketToDev(@PathVariable("ticket_id") int ticket_id, @PathVariable("dev_id") int dev_id) {
        return ResponseEntity.ok(ticketService.assignToDev(ticket_id, dev_id));
    }

    @GetMapping("/apps")
    public ResponseEntity<List<Software>> getAllApps() {
        return ResponseEntity.ok(softwareService.findAll());
    }

    @PostMapping("/apps")
    public ResponseEntity<Software> addApp(@RequestBody Software software) {
        return ResponseEntity.status(HttpStatus.CREATED).body(softwareService.add(software));
    }
}
