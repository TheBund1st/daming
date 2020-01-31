package com.thebund1st.daming.application.commandhandling.interceptor

import com.thebund1st.paa.command.interceptor.*
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import spock.lang.Specification

@Import(TargetCommandHandlerTestConfiguration)
@SpringBootTest
class CommandHandlerInterceptorAspectTest extends Specification {

    @Autowired
    private CommandHandlerWithReturn commandHandlerWithReturn

    @Autowired
    private CommandHandlerWithoutReturn commandHandlerWithoutReturn

    @SpringBean
    private DefaultCommandHandlerWithReturn.Dependency dependency = Mock()

    def "it should intercept command handler method"() {
        given:
        Command command = new Command()
        Return result = new Return()

        and:
        dependency.handle(command) >> result

        when:
        commandHandlerWithReturn.handle(command)

        then:
        assert command.flags == ['1', '2'] // assert the interceptors preHandles with ascending sort
        assert result.flags == ['2', '1'] // assert the interceptors postHandles with descending sort
    }

    def "it should not break when command handler without return"() {
        given:
        AnotherCommand command = new AnotherCommand()

        when:
        commandHandlerWithoutReturn.handle(command)

        then:
        assert command.flags == ['1']
    }

}
