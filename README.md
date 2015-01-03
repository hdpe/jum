jum - JUnit Mixins
==================

_Is there nothing to be said for this?_

The idea here is to facilitate test code reuse by providing a mechanism for JUnit to compose test suites from a collection of classes, each with their own `@Test` annotated methods.

This is similar to the functionality provided by the JUnit `Suite` runner, but it additionally injects an instance of the calling class into the included class to allow for test parameterisation.

This provides an alternative to the traditional mechanisms of test method reuse (inheriting test methods from superclasses, using parameterised tests, or - most commonly - copy-pasting tests all over the place like a sociopath.) I'm not a fan of these approaches and I, probably naively, think this is an improvement.

I eagerly await being told the good reason why this approach has never previously existed!

## Getting Started

Clone the repo, import into Eclipse with _File -> Import -> Maven -> Existing Maven Projects_, right-click then _Run As -> JUnit Test_ the _jum-examples_ project to see it in action.

## Why on Earth..?

Say you've got an application screen, and some tests for a component:

```
@Test public void clickDoesWhatItShould() { ... }

@Test public void enterKeyDoesWhatItShould() { ... }

...
```

Now we make another screen that uses the same component. We could copy-paste the existing tests, changing them where necessary to allow for the new context. But we're not animals! We would ideally like to move the tests up into an abstract superclass.

```
abstract class AbstractComponent1Tests {
  @Test public void clickDoesWhatItShould() {
    Stuff stuff = doComponent1Setup();
    ...
  }
}

class Screen1 extends AbstractComponent1Tests {
  @Override public Stuff doComponentSetup() { ... }
}

class Screen2 extends AbstractComponent1Tests {
  @Override public Stuff doComponentSetup() { ... }
}
```

But this is horrible! We can only inherit from one superclass in Java. What happens when we introduce AbstractComponent2Tests, AbstractComponent3Tests...? And Screen3, Screen4...? We may split it up into Screen3Component1Tests, Screen3Component2Tests etc., but we will inevitably end up with an absolute explosion of subclasses that do nothing other than the setup necessary for testing the component. So we don't do it. So we just copy-paste the tests instead, creating a maintenance timebomb.

There has to be a better way.

To be continued.
