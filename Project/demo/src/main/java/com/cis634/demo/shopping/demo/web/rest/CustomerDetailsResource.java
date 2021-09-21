package com.cis634.demo.shopping.demo.web.rest;

import com.cis634.demo.shopping.demo.domain.CustomerDetails;
import com.cis634.demo.shopping.demo.service.CustomerDetailsService;
import com.cis634.demo.shopping.demo.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.adyen.demo.store.domain.CustomerDetails}.
 */
@RestController
@RequestMapping("/api")
public class CustomerDetailsResource {

    private final Logger log = LoggerFactory.getLogger(CustomerDetailsResource.class);

    private static final String ENTITY_NAME = "customerDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerDetailsService customerDetailsService;

    public CustomerDetailsResource(CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }

    
    @PostMapping("/customer-details")
    public ResponseEntity<CustomerDetails> createCustomerDetails(@Valid @RequestBody CustomerDetails customerDetails) throws URISyntaxException {
        log.debug("REST request to save CustomerDetails : {}", customerDetails);
        if (customerDetails.getId() != null) {
            throw new BadRequestAlertException("A new customerDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerDetails result = customerDetailsService.save(customerDetails);
        return ResponseEntity.created(new URI("/api/customer-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

   
    @PutMapping("/customer-details")
    public ResponseEntity<CustomerDetails> updateCustomerDetails(@Valid @RequestBody CustomerDetails customerDetails) throws URISyntaxException {
        log.debug("REST request to update CustomerDetails : {}", customerDetails);
        if (customerDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerDetails result = customerDetailsService.save(customerDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customerDetails.getId().toString()))
            .body(result);
    }

    
    @GetMapping("/customer-details")
    public ResponseEntity<List<CustomerDetails>> getAllCustomerDetails(Pageable pageable) {
        log.debug("REST request to get a page of CustomerDetails");
        Page<CustomerDetails> page = customerDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

   
}
