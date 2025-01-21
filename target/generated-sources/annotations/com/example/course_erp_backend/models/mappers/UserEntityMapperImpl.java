package com.example.course_erp_backend.models.mappers;

import com.example.course_erp_backend.models.enums.user.UserStatus;
import com.example.course_erp_backend.models.mybatis.user.User;
import com.example.course_erp_backend.models.payload.auth.SignUpPayload;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-18T16:43:28+0400",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
public class UserEntityMapperImpl implements UserEntityMapper {

    @Override
    public User fromSignUpPayloadToUser(SignUpPayload payload, String encryptedPassword, Long roleId) {
        if ( payload == null && encryptedPassword == null && roleId == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        if ( payload != null ) {
            user.name( payload.getName() );
            user.surname( payload.getSurname() );
            user.email( payload.getEmail() );
            user.phoneNumber( payload.getPhoneNumber() );
        }
        user.password( encryptedPassword );
        user.roleId( roleId );
        user.status( UserStatus.ACTIVE );

        return user.build();
    }
}
