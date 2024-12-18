package com.bmt.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.bmt.webapp.models.Client;
import com.bmt.webapp.models.ClientDto;
import com.bmt.webapp.repositories.ClientRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientRepository clientRepo;

    @GetMapping({"", "/"})
    public String getClients(Model model) {
        var clients = clientRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (clients.isEmpty()) {
            System.out.println("No clients found!");
        }
        model.addAttribute("clients", clients);
        return "clients/index";
    }

    @GetMapping("/create")
    public String createClient(Model model) {
        ClientDto clientDto = new ClientDto();
        model.addAttribute("clientdto", clientDto);
        return "clients/create";
    }

    @PostMapping("/create")
    public String saveClient(@ModelAttribute @Valid ClientDto clientDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "clients/create";  // return to the form if validation fails
        }
        Client client = new Client();
        client.setFirstname(clientDto.getFirstname());
        client.setLastname(clientDto.getLastname());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        client.setAddress(clientDto.getAddress());
        client.setStatus(clientDto.getStatus());
        client.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));

        clientRepo.save(client); // Save client to the database
        return "redirect:/clients"; // Redirect back to the list of clients
    }

    @GetMapping("/edit/{id}")
    public String editClient(@PathVariable("id") Integer id, Model model) {
        Client client = clientRepo.findById(id).orElse(null);
        if (client == null) {
            return "redirect:/clients"; // Redirect if client not found
        }
        model.addAttribute("client", client); // Add client to model

        ClientDto clientDto = new ClientDto();
        clientDto.setFirstname(client.getFirstname());
        clientDto.setLastname(client.getLastname());
        clientDto.setEmail(client.getEmail());
        clientDto.setPhone(client.getPhone());
        clientDto.setAddress(client.getAddress());
        clientDto.setStatus(client.getStatus());

        model.addAttribute("clientdto", clientDto);
        model.addAttribute("clientId", id); // Pass client ID to the form
        return "clients/edit"; // Edit page
    }

    @PostMapping("/edit/{id}")
    public String saveClient(@PathVariable("id") Integer id, @ModelAttribute("clientdto") ClientDto clientDto, BindingResult result) {
        if (result.hasErrors()) {
            return "clients/edit"; // If validation fails, return to the edit page
        }

        Client client = clientRepo.findById(id).orElse(null);
        if (client == null) {
            return "redirect:/clients"; // If client is not found, redirect to clients list
        }

        // Update client details
        client.setFirstname(clientDto.getFirstname());
        client.setLastname(clientDto.getLastname());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        client.setAddress(clientDto.getAddress());
        client.setStatus(clientDto.getStatus());

        clientRepo.save(client); // Save updated client details

        return "redirect:/clients"; // Redirect to the clients list after saving
    }
    
    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") Integer id) {
        clientRepo.deleteById(id); // Delete the client by ID
        return "redirect:/clients"; // Redirect to clients list
    }

}
