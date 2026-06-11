package com.studentnet.config;

import com.studentnet.model.Opportunity;
import com.studentnet.model.Post;
import com.studentnet.model.User;
import com.studentnet.repository.OpportunityRepository;
import com.studentnet.repository.PostRepository;
import com.studentnet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private UserRepository userRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private OpportunityRepository opportunityRepository;
    @Autowired private PasswordEncoder passwordEncoder;

   @Override
public void run(String... args) {
    // Only seed if no users exist
    if (userRepository.count() > 0) {
        System.out.println("--- Data already seeded, skipping ---");
        return;
    }

    User alice = User.builder()
            .fullName("Alice Sharma")
            .email("alice@example.com")
            .password(passwordEncoder.encode("password123"))
            .university("IIT Madras")
            .department("Computer Science")
            .yearOfStudy("3rd")
            .bio("Passionate about ML and open-source.")
            .skills("Python, TensorFlow, Java, Data Structures")
            .githubUrl("https://github.com/alicesharma")
            .build();

    User bob = User.builder()
            .fullName("Bob Krishnan")
            .email("bob@example.com")
            .password(passwordEncoder.encode("password123"))
            .university("NIT Trichy")
            .department("Electronics & Communication")
            .yearOfStudy("4th")
            .bio("Final year student. Exploring embedded systems and IoT.")
            .skills("C++, Arduino, MATLAB, IoT, PCB Design")
            .build();

    User carol = User.builder()
            .fullName("Carol Rajan")
            .email("carol@example.com")
            .password(passwordEncoder.encode("password123"))
            .university("Anna University")
            .department("Information Technology")
            .yearOfStudy("2nd")
            .bio("Web developer and UI/UX enthusiast.")
            .skills("React, Node.js, Figma, MongoDB, TypeScript")
            .build();

    userRepository.save(alice);
    userRepository.save(bob);
    userRepository.save(carol);

    alice.getConnections().add(bob);
    bob.getConnections().add(alice);
    userRepository.save(alice);
    userRepository.save(bob);

    Post p1 = Post.builder()
            .author(alice)
            .content("Just finished implementing a transformer from scratch in PyTorch!")
            .type(Post.PostType.RESOURCE)
            .resourceUrl("https://jalammar.github.io/illustrated-transformer/")
            .resourceTitle("The Illustrated Transformer")
            .tags("Machine Learning, Deep Learning, NLP")
            .build();

    Post p2 = Post.builder()
            .author(bob)
            .content("Completed my final year project on Smart Agriculture using IoT!")
            .type(Post.PostType.ACHIEVEMENT)
            .tags("IoT, Embedded Systems, Agriculture Tech")
            .build();

    postRepository.save(p1);
    postRepository.save(p2);

    Opportunity opp1 = Opportunity.builder()
            .postedBy(alice)
            .title("ML Research Intern - IIT Madras")
            .description("Work on NLP and computer vision research with faculty mentorship.")
            .type(Opportunity.OpportunityType.RESEARCH)
            .company("IIT Madras - RBCDSAI Lab")
            .location("Chennai / Remote")
            .isPaid(true)
            .stipend("₹10,000/month")
            .requiredSkills("Python, PyTorch, Linear Algebra")
            .deadline(java.time.LocalDate.now().plusMonths(1))
            .build();

    opportunityRepository.save(opp1);

    System.out.println("--- Sample data seeded ---");
}
}
