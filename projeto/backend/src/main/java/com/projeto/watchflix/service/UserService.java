package com.projeto.watchflix.service;

import com.projeto.watchflix.model.Channel;
import com.projeto.watchflix.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final ChannelRepository channelRepository;

    @Transactional(propagation = Propagation.NEVER)
    public void registerChannel(MultipartFile file,String firstName,
                                String lastName, String fullName,String email,String password) throws IOException {

                Channel channel = new Channel();

                byte[] imageBytes = file.getBytes();

                channel.setActive(true);
                channel.setFirstName(firstName);
                channel.setLastName(lastName);
                channel.setFullName(fullName);
                channel.setEmailAddress(email);
                channel.setPassword(password);
                channel.setProfileImage(imageBytes);

                channelRepository.registerChannel(channel);

    }

    public List<Map<String, Object>> findChannel() {
       return channelRepository.findAllChannels();


    }
    @Transactional(propagation = Propagation.NEVER)
    public  Object findChannelById(Long id) {
        return channelRepository.findChannelById(id);
    }


    public void deleteChannel(Long id) {
        channelRepository.deleteChannel(id);
    }


}