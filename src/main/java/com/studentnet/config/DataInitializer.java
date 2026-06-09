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
        // Create sample students
        User alice = User.builder()
                .fullName("Alice Sharma")
                .email("alice@example.com")
                .password(passwordEncoder.encode("password123"))
                .university("IIT Madras")
                .department("Computer Science")
                .yearOfStudy("3rd")
                .bio("Passionate about ML and open-source. Looking for research opportunities.")
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
                .linkedinUrl("https://linkedin.com/in/bobkrishnan")
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

        // Connect alice and bob
        alice.getConnections().add(bob);
        bob.getConnections().add(alice);
        userRepository.save(alice);
        userRepository.save(bob);

        // Sample posts
        Post p1 = Post.builder()
                .author(alice)
                .content("Just finished implementing a transformer from scratch in PyTorch! " +
                         "Here's a beginner-friendly guide that helped me.")
                .type(Post.PostType.RESOURCE)
                .resourceUrl("https://jalammar.github.io/illustrated-transformer/")
                .resourceTitle("The Illustrated Transformer")
                .tags("Machine Learning, Deep Learning, NLP, Python")
                .build();

        Post p2 = Post.builder()
                .author(bob)
                .content("Excited to share that I just completed my final year project on " +
                         "Smart Agriculture using IoT sensors and MQTT protocol! " +
                         "Open to collaboration for paper submission.")
                .type(Post.PostType.ACHIEVEMENT)
                .tags("IoT, Embedded Systems, Agriculture Tech")
                .build();

        Post p3 = Post.builder()
                .author(carol)
                .content("Anyone working on a React project and need a UI/UX collaborator? " +
                         "I'm looking for project opportunities to build my portfolio.")
                .type(Post.PostType.GENERAL)
                .tags("React, Web Dev, Collaboration, Portfolio")
                .build();

        postRepository.save(p1);
        postRepository.save(p2);
        postRepository.save(p3);

        // Sample opportunities
        Opportunity opp1 = Opportunity.builder()
                .postedBy(alice)
                .title("ML Research Intern - IIT Madras RBCDSAI")
                .description("We are looking for passionate students interested in NLP and " +
                             "computer vision research. You will work on real datasets, " +
                             "write papers, and get mentorship from faculty.")
                .type(Opportunity.OpportunityType.RESEARCH)
                .company("IIT Madras - RBCDSAI Lab")
                .location("Chennai / Remote")
                .isPaid(true)
                .stipend("₹10,000/month")

                .requiredSkills("Python, PyTorch or TensorFlow, Linear Algebra")
                .deadline(LocalDate.now().plusMonths(1))
                .build();

        Opportunity opp2 = Opportunity.builder()
                .postedBy(carol)
                .title("Full Stack Developer Internship - EdTech Startup")
                .description("Join our small team building a next-gen learning platform. " +
                             "You'll own features end-to-end and ship real code.")
                .type(Opportunity.OpportunityType.INTERNSHIP)
                .company("LearnUp Technologies")
                .location("Bangalore (Hybrid)")
                .isPaid(true)
                .stipend("₹20,000/month")
                .requiredSkills("React, Node.js, MongoDB, REST APIs")
                .applyUrl("https://learnup.tech/careers")
                .deadline(LocalDate.now().plusWeeks(3))
                .build();

        opportunityRepository.save(opp1);
        opportunityRepository.save(opp2);

        System.out.println("\n--- Sample data seeded ---");
        System.out.println("Login with: alice@example.com / password123");
        System.out.println("Login with: bob@example.com / password123");
        System.out.println("Login with: carol@example.com / password123");
        System.out.println("---------------------------\n");
    }
}
