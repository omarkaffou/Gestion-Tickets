package emsi.chakir_kaffou.devoir.controllers;

import java.util.List;

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

import emsi.chakir_kaffou.devoir.models.Dev;
import emsi.chakir_kaffou.devoir.models.Status;
import emsi.chakir_kaffou.devoir.models.Ticket;
import emsi.chakir_kaffou.devoir.services.TicketService;
import emsi.chakir_kaffou.devoir.services.UserService;
import emsi.chakir_kaffou.devoir.utils.Constants;

@RestController
@RequestMapping(Constants.API_PREFIX + "/devs")
public class DevController {
    
    @Autowired
    private UserService<Dev> userService;
    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<Dev>> getAll() {
        return ResponseEntity.ok(userService.findByRole(Constants.getRole("dev")));
    }
    
    @PostMapping
    public ResponseEntity<Dev> add(@RequestBody Dev dev) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(dev));
    }

    @GetMapping("/{dev_id}/tickets")
    public ResponseEntity<List<Ticket>> getAllTickets(@PathVariable("dev_id") int dev_id) {
        return ResponseEntity.ok(ticketService.findByDevId(dev_id));
    }

    @PutMapping("/{dev_id}/tickets/{ticket_id}")
    public ResponseEntity<Ticket> updateStatus(@RequestParam(name = "status", required = true) String status,
            @PathVariable("dev_id") int dev_id, @PathVariable("ticket_id") int ticket_id) {
        if (dev_id != ticketService.findById(ticket_id).getDev().getId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(ticketService.updateStatus(Status.valueOf(status.toUpperCase()), ticket_id));
    }

}
