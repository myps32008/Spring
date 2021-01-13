package viettel.CQRSES.Domain.Contracts.Producers;

import viettel.CQRSES.Domain.Entities.User;
import viettel.CQRSES.Events.BaseEvent;

public interface IUserProducer {
    void sendMessage(BaseEvent eventUser);
}
