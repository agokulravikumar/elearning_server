package com.edtech.elearning.service;

import com.edtech.elearning.constants.Constants;
import com.edtech.elearning.constants.MsgConstants;
import com.edtech.elearning.entity.UsersEntity;
import com.edtech.elearning.model.User;
import com.edtech.elearning.repo.UsersRepo;
import com.edtech.elearning.util.ModelConverter;
import com.edtech.elearning.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UsersRepo usersRepo;
    private final ModelConverter modelConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity usersEntity = usersRepo.findByUsername(username);

        if (usersEntity == null) {
            throw new UsernameNotFoundException(MsgConstants.USER_NOT_AVAILABLE);
        }

        return modelConverter.map(usersEntity, User.class);
    }

    public ResponseEntity<String> createUser(User user) {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUsername(user.getUsername());
        usersEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersEntity.setStatus(Constants.ACTIVE);

        usersRepo.save(usersEntity);

        return Response.success();
    }
}