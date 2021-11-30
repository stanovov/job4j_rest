package ru.job4j.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;

import org.hamcrest.MatcherAssert;
import com.google.gson.Gson;
import org.mockito.ArgumentCaptor;
import ru.job4j.rest.Job4jRestApplication;
import ru.job4j.rest.domain.Person;
import ru.job4j.rest.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = Job4jRestApplication.class)
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private Gson gson;

    @Test
    public void whenNoPeopleAndFindAllShouldReturnIsOk() throws Exception {
        when(personRepository.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(List.of())));
    }

    @Test
    public void whenThreePeopleExistAndFindAllShouldReturnIsOk() throws Exception {
        List<Person> people = List.of(
                Person.of(1, "Person 1", "Pwd 1"),
                Person.of(2, "Person 2", "Pwd 2"),
                Person.of(3, "Person 3", "Pwd 3")
        );
        when(personRepository.findAll()).thenReturn(people);
        mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(people)));
    }

    @Test
    public void whenPersonExistsAndFindByIdShouldReturnIsOk() throws Exception {
        Person person = Person.of(
                99,
                "Person 99",
                "PWD TEST"
        );
        when(personRepository.findById(99)).thenReturn(Optional.of(person));
        mockMvc.perform(get("/person/99"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(person)));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(personRepository).findById(argument.capture());
        MatcherAssert.assertThat(argument.getValue(), is(99));
    }

    @Test
    public void whenPersonDoesNotExistAndFindByIdShouldReturnIsNotFound() throws Exception {
        when(personRepository.findById(1)).thenReturn(Optional.empty());
        mockMvc.perform(get("/person/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(new Person())));
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(personRepository).findById(argument.capture());
        MatcherAssert.assertThat(argument.getValue(), is(1));
    }

    @Test
    public void whenCorrectPostReqShouldReturnIsCreated() throws Exception {
        Person person = Person.of("new person", "test");
        Person rsl = Person.of(1, "new person", "test");
        when(personRepository.save(person)).thenReturn(rsl);
        mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(person)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.login", equalTo("new person")))
                .andExpect(jsonPath("$.password", equalTo("test")));
        ArgumentCaptor<Person> personArgument = ArgumentCaptor.forClass(Person.class);
        verify(personRepository).save(personArgument.capture());
        MatcherAssert.assertThat(personArgument.getValue().getLogin(), is("new person"));
        MatcherAssert.assertThat(personArgument.getValue().getPassword(), is("test"));
    }

    @Test
    public void whenIncorrectPostReqShouldReturnIs4xxClientError() throws Exception {
        mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("incorrect json"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenPutReqShouldReturnIsOk() throws Exception {
        Person person = Person.of(99, "updated person", "pwd");
        mockMvc.perform(put("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(person)))
                .andExpect(status().isOk());
        ArgumentCaptor<Person> personArgument = ArgumentCaptor.forClass(Person.class);
        verify(personRepository).save(personArgument.capture());
        MatcherAssert.assertThat(personArgument.getValue().getId(), is(99));
        MatcherAssert.assertThat(personArgument.getValue().getLogin(), is("updated person"));
        MatcherAssert.assertThat(personArgument.getValue().getPassword(), is("pwd"));
    }

    @Test
    public void whenDeleteReqShouldReturnIsOk() throws Exception {
        mockMvc.perform(delete("/person/1"))
                .andExpect(status().isOk());
        ArgumentCaptor<Person> personArgument = ArgumentCaptor.forClass(Person.class);
        verify(personRepository).delete(personArgument.capture());
        MatcherAssert.assertThat(personArgument.getValue().getId(), is(1));
    }
}