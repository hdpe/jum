# jum - JUnit Mixins

The idea here is to facilitate test code reuse by providing a mechanism for JUnit to compose test suites from a collection of classes, each with their own `@Test` annotated methods.

This is similar to the functionality provided by the JUnit `Suite` runner, but it additionally injects an instance of the calling class into the included class to allow for test parameterisation.

This provides an alternative to the traditional mechanisms of test method reuse (inheriting test methods from superclasses, using parameterised tests, or - most commonly - copy-pasting tests all over the place like a sociopath.) I'm not a fan of these approaches and I, probably naively, think this is an improvement.

I eagerly await being told the good reason why this approach has never previously existed!

## Getting started

Clone the repo, import into Eclipse with _File -> Import -> Maven -> Existing Maven Projects_, right-click then _Run As -> JUnit Test_ the _jum-examples_ project to see it in action.

## Why on Earth..?

Say you've got an application screen, and some tests for a component:

```java
@Test public void clickDoesWhatItShould() { ... }

@Test public void enterKeyDoesWhatItShould() { ... }

...
```

Now we make another screen that uses the same component. We could copy-paste the existing tests, changing them where necessary to allow for the new context. But we're not animals! We would ideally like to move the tests up into an abstract superclass.

### The trouble with inheriting tests

So we end up with something like this:

```java
abstract class AbstractComponentTest {
  @Test public void clickDoesWhatItShould() {
    Stuff stuff = doComponentSetup();
    ...
  }
}

class Screen1 extends AbstractComponentTest {
  @Override public Stuff doComponentSetup() { ... }
}

class Screen2 extends AbstractComponentTest {
  @Override public Stuff doComponentSetup() { ... }
}
```

But this is horrible!

We can only inherit from one superclass in Java. What happens when we introduce `AbstractAnotherComponentTest`? What about `Screen3`, `Screen4`...? We may split it up into `Screen3AnotherComponentTest`, `Screen3YetAnotherComponentTest` etc., but in doing so we will inevitably end up with an explosion of subclasses that do nothing other than the setup necessary for testing the component.

So we don't do it. We just copy-paste the tests instead, lackadaisically creating a maintenance timebomb.

There has to be a better way.

### The trouble with parameterised tests

It's been suggested to me that you can also reuse tests by parameterising them, a la https://github.com/junit-team/junit/wiki/Parameterized-tests.

To me this feels backwards - the component under test would need to be aware of all the contexts that it will be tested in, not the other way round. There's also no potential for, e.g., adding a single specific test for a given context using this approach.

For now, I'm going to leave this idea for dead, but I reserve the right to broadside against it vigorously should it resurface.

## jum's approach

Let's turn the problem on its head. The test class for `Screen1` should simply be `Screen1Test`, and this should just include - or "mix in" - the tests for the components that `Screen1` uses.

```java
@RunWith(MixinRunner.class)
@Mixins({Component1Tests.class})
class Screen1Test implements SupportsComponent1Tests {
  @Override public Stuff doComponent1Setup() { ... }
}

abstract class Component1Tests {
	interface SupportsComponent1Tests {
	  Stuff doComponent1Setup();
	}
  
  @TestReference public SupportsComponent1Tests test;
  
  @Test public void clickDoesWhatItShould() {
    Stuff stuff = test.doComponent1Setup();
    ...
  }
}
```

There's a few things we've done here. We've:

* Packaged our tests into a 'component' class which we've made `abstract`. jum will actually dynamically subclass and instantiate this class with Javassist - the reason we make it abstract is to indicate that these tests can only be executed when included as a mixin, so IDE test runners won't try to run the component alone.
* Made our component implement an interface that defines the methods expected of tests this component will be used in.
* Annotated a field with `@TestReference` - jum will inject an instance of the calling test class here.
* Specified the jum `MixinRunner` as the JUnit runner, which dynamically includes the classes listed in the `@Mixins` annotation into the test execution.

To my mind the advantage of such an approach over inheriting tests from an abstract superclass is clear: we can introduce a `Component2` without having to create a parallel test hierarchy. We just implement the required setup methods in our test and add the new component class to the `@Mixins` annotation:

```java
@Mixins({Component1Tests.class, Component2Tests.class, ...})
class Screen1Test implements SupportsComponent1Tests, SupportsComponent2Tests, ... {
  @Override public Stuff doComponent1Setup() { ... }
  
  @Override public Stuff doComponent2Setup() { ... }
  
  ...
}
```

This is a good thing... right? Right?

## Disclaimer

May not work, may be a terrible idea, subject to change without notice.
