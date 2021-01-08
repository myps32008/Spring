package viettel.CQRSES.AppService.Service;

import org.springframework.stereotype.Service;
import viettel.CQRSES.Domain.Contracts.Aggregates.IUserAggregate;
import viettel.CQRSES.Domain.Contracts.Projections.IUserProjection;
import viettel.CQRSES.Domain.Contracts.Services.IUserService;

@Service
public class UserService implements IUserService {
    private final IUserAggregate userAggregate;
    private final IUserProjection userProjection;

    public UserService(IUserAggregate userAggregate, IUserProjection userProjection) {
        this.userAggregate = userAggregate;
        this.userProjection = userProjection;
    }
}
