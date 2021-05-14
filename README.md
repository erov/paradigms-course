# ITMO-paradigms-course
Мои решения домашних работ курса ["Парадигмы программирования"](http://www.kgeorgiy.info//courses/paradigms/index.html)  
В заданиях реализованы модификации с отметкой 38-39, а также сложные версии там, где есть разделение.  

## Домашнее задание 1. Бинарный поиск

> Реализация: [BinarySearchMax.java](/hw01-BinarySearch/src/search/BinarySearchMax.java)

1. Реализуйте итеративный и рекурсивный варианты бинарного поиска в массиве.  
2. На вход подается целое число x и массив целых чисел a, отсортированный по невозрастанию. Требуется найти минимальное значение индекса i, при котором a[i] <= x.  
3. Для функций бинарного поиска и вспомогательных функций должны быть указаны, пред- и постусловия. Для реализаций методов должны быть приведены доказательства соблюдения контрактов в терминах троек Хоара.  
4. Интерфейс программы.  
    * Имя основного класса — `BinarySearch`.
    * Первый аргумент командной строки — число `x`.
    * Последующие аргументы командной строки — элементы массива `a`.
5. Пример запуска: `java BinarySearch 3 5 4 3 2 1`. Ожидаемый результат: `2`.  

Модификации:
 * *Базовая*
    * Класс `BinarySearch` должен находиться в пакете `search`
    * [Исходный код тестов](/hw01-BinarySearch/src/search/BinarySearchTest.java)
 * *Max* (38-39)
    * На вход подается массив полученный приписыванием 
      отсортированного (строго) по убыванию массива 
      в конец массива отсортированного (строго) по возрастанию
      Требуется найти в нем максимальное значение.
    * Класс должен иметь имя `BinarySearchMax`
    * [Исходный код тестов](/hw01-BinarySearch/src/search/BinarySearchMaxTest.java)

## Домашнее задание 2. Очередь на массиве

> Реализация: 
>   * [ArrayQueueModule.java](hw02%2B03-ArrayQueue%2BQueue/src/queue/ArrayQueueModule.java)
>   * [ArrayQueueADT.java](hw02%2B03-ArrayQueue%2BQueue/src/queue/ArrayQueueADT.java)
>   * [ArrayQueue.java](hw02%2B03-ArrayQueue%2BQueue/src/queue/ArrayQueue.java)  
>   
> Тесты:
>   * [ArrayQueueModuleTest.java](hw02%2B03-ArrayQueue%2BQueue/src/queue/ArrayQueueModuleTest.java)
>   * [ArrayQueueADTTest.java](hw02%2B03-ArrayQueue%2BQueue/src/queue/ArrayQueueADTTest.java)
>   * [ArrayQueueClassTest.java](hw02%2B03-ArrayQueue%2BQueue/src/queue/ArrayQueueClassTest.java)

1. Определите модель и найдите инвариант структуры данных «[очередь](https://ru.wikipedia.org/wiki/Очередь_(программирование))». Определите функции, которые необходимы для реализации очереди. Найдите их пред- и постусловия, при условии что очередь не содержит `null`.  
2. Реализуйте классы, представляющие циклическую очередь с применением массива.  
    * Класс `ArrayQueueModule` должен реализовывать один экземпляр очереди с использованием переменных класса.
    * Класс `ArrayQueueADT` должен реализовывать очередь в виде абстрактного типа данных (с явной передачей ссылки на экземпляр очереди).
    * Класс `ArrayQueue` должен реализовывать очередь в виде класса (с неявной передачей ссылки на экземпляр очереди).
    * Должны быть реализованы следующие функции (процедуры) / методы:
        * `enqueue` – добавить элемент в очередь;
        * `element` – первый элемент в очереди;
        * `dequeue` – удалить и вернуть первый элемент в очереди;
        * `size` – текущий размер очереди;
        * `isEmpty` – является ли очередь пустой;
        * `clear` – удалить все элементы из очереди.
    * Инвариант, пред- и постусловия записываются в исходном коде в виде комментариев.
    * Обратите внимание на инкапсуляцию данных и кода во всех трех реализациях.
3. Напишите тесты к реализованным классам.  

Модификации:
 * *Базовая*
    * Классы должны находиться в пакете `queue`
    * [Исходный код тестов](hw02%2B03-ArrayQueue%2BQueue/src/queue/ArrayQueueTest.java)
 * *Deque* (36-37)
    * Реализовать методы
        * `push` – добавить элемент в начало очереди
        * `peek` – вернуть последний элемент в очереди
        * `remove` – вернуть и удалить последний элемент из очереди
    * [Исходный код тестов](hw02%2B03-ArrayQueue%2BQueue/src/queue/ArrayDequeTest.java)
 * *DequeToStrArray* (38-39)
    * Реализовать модификацию *Deque*
    * Реализовать метод `toArray`, возвращающий массив,
      содержащий элементы, лежащие в очереди в порядке
      от головы к хвосту.
    * Реализовать метод `toStr`, возвращающий строковое представление
      очереди в виде '`[`' _голова_ '`, `' ... '`, `' _хвост_ '`]`'
    * [Исходный код тестов](hw02%2B03-ArrayQueue%2BQueue/src/queue/ArrayDequeToStrArrayTest.java)

## Домашнее задание 3. Очереди

> Реализация: [LinkedQueue.java](hw02%2B03-ArrayQueue%2BQueue/src/queue/LinkedQueue.java)

1. Определите интерфейс очереди `Queue` и опишите его контракт.  
2. Реализуйте класс `LinkedQueue` — очередь на связном списке.  
3. Выделите общие части классов `LinkedQueue` и `ArrayQueue` в базовый класс `AbstractQueue`.  
Это домашнее задание связанно с предыдущим.  

Модификации:
 * *Базовая*
    * [Исходный код тестов](hw02%2B03-ArrayQueue%2BQueue/src/queue/QueueTest.java)
 * *Nth* (38-39)
    * Добавить в интерфейс очереди и реализовать методы
        * `getNth(n)` – создать очередь, содержащую каждый n-й элемент, считая с 1
        * `removeNth(n)` – создать очередь, содержащую каждый n-й элемент, и удалить их из исходной очереди
        * `dropNth(n)` – удалить каждый n-й элемент из исходной очереди
    * Тип возвращаемой очереди должен соответствовать типу исходной очереди
    * Дублирования кода быть не должно
    * [Исходный код тестов](hw02%2B03-ArrayQueue%2BQueue/src/queue/QueueNthTest.java)

## Домашнее задание 4. Вычисление в различных типах

> Реализация: [GenericTabulator.java](hw04-Generics/src/expression/generic/GenericTabulator.java)

Добавьте в [программу разбирающую и вычисляющую выражения трех переменных](https://github.com/iamerove/ITMO-prog-intro-course#домашнее-задание-12-обработка-ошибок) поддержку вычисления в различных типах.

1. Создайте класс `expression.generic.GenericTabulator`, реализующий интерфейс `expression.generic.Tabulator`:
    ```
    public interface Tabulator {
        Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception;
    }
    ```
    
    Аргументы:
    * mode — режим работы
    
        | Режим | Тип |
        | - | - |
        | i | int (с детекцией переполнений) |
        | d | double |
        | bi | [BigInteger](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/BigInteger.html) |
    * expression — вычисляемое выражение;
    * `x1, x2; y1, y2; z1, z2` — диапазоны изменения переменны (включительно).
    
    Возвращаемое значение — таблица значений функции, где `R[i][j][k]` соответствует `x = x1 + i`, `y = y1 + j`, `z = z1 + k`. Если вычисление завершилось ошибкой, в соответствующей ячейке должен быть `null`.  
2. Доработайте интерфейс командной строки:
    * Первым аргументом командной строки программа должна принимать указание на тип, в котором будут производится вычисления:

        | Опция | Тип |
        | - | - |
        | -i | int (с детекцией переполнений) |
        | -d | double |
        |-bi | [BigInteger](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/BigInteger.html) |
    * Вторым аргументом командной строки программа должна принимать выражение для вычисления.
    * Программа должна выводить результаты вычисления для всех целочисленных значений переменных из диапазона `−2..2`.
3. Реализация не должна содержать [непроверяемых преобразований типов](https://docs.oracle.com/javase/specs/jls/se11/html/jls-5.html#jls-5.1.9).  
4. Реализация не должна использовать аннотацию [@SuppressWarnings](https://docs.oracle.com/javase/specs/jls/se11/html/jls-9.html#jls-9.6.4.5).  
5. При выполнении задания следует обратить внимание на простоту добавления новых типов и операциий.  

Модификации
 * *Базовая*
    * Класс `GenericTabulator` должен реализовывать интерфейс
      [Tabulator](hw04-Generics/src/expression/generic/Tabulator.java) и
      сроить трехмерную таблицу значений заданного выражения.
        * `mode` – режим вычислений:
           * `i` – вычисления в `int` с проверкой на переполнение;
           * `d` – вычисления в `double` без проверки на переполнение;
           * `bi` – вычисления в `BigInteger`.
        * `expression` – выражение, для которого надо построить таблицу;
        * `x1`, `x2` – минимальное и максимальное значения переменной `x` (включительно)
        * `y1`, `y2`, `z1`, `z2` – аналогично для `y` и `z`.
        * Результат: элемент `result[i][j][k]` должен содержать
          значение выражения для `x = x1 + i`, `y = y1 + j`, `z = z1 + k`.
          Если значение не определено (например, по причине переполнения),
          то соответствующий элемент должен быть равен `null`.
    * [Исходный код тестов](hw04-Generics/src/expression/generic/GenericTest.java)
 * *AsmUpb* (38-39)
    * Дополнительно реализовать унарные операции:
        * `abs` – модуль числа, `abs -5` равно 5;
        * `square` – возведение в квадрат, `square 5` равно 25.
    * Дополнительно реализовать бинарную операцию (максимальный приоритет):
        * `mod` – взятие по модулю, приоритет как у умножения (`1 + 5 mod 3` равно `1 + (5 mod 3)` равно `3`).
    * Дополнительно реализовать поддержку режимов:
        * `u` – вычисления в `int` без проверки на переполнение;
        * `p` – вычисления в целых числах по модулю 1009;
        * `b` – вычисления в `byte` без проверки на переполнение.
    * [Исходный код тестов](hw04-Generics/src/expression/generic/GenericAsmUpbTest.java)

## Домашнее задание 5. Функциональные выражения на JavaScript

> Реализация: [functionalExpression.js](/hw05-JS-FunctionalExpression/functionalExpression.js)

1. Разработайте функции `cnst`, `variable`, `add`, `subtract`, `multiply`, `divide`, `negate` для вычисления выражений с одной переменной.  
2. Функции должны позволять производить вычисления вида:
   ```
   let expr = subtract(
       multiply(
           cnst(2),
           variable("x")
       ),
       cnst(3)
   );

   println(expr(5));
   ```

   При вычислении такого выражения вместо каждой переменной подставляется значение, переданное в качестве параметра функции `expr` (на данном этапе имена переменных игнорируются). Таким образом, результатом вычисления приведенного примера должно стать число 7.  

3. Тестовая программа должна вычислять выражение `x^2−2x+1`, для x от 0 до 10.  
4. _Сложный вариант_. Требуется дополнительно написать функцию `parse`, осуществляющую разбор выражений, записанных в [обратной польской записи](https://ru.wikipedia.org/wiki/Обратная_польская_запись). Например, результатом  
   ```
   parse("x x 2 - * x * 1 +")(5)
   ```
   
   должно быть число 76.  
5. При выполнение задания следует обратить внимание на:
   * Применение функций высшего порядка.
   * Выделение общего кода для операций.

Модификации:
 * *Базовая*
    * [Исходный код тестов](javascript/jstest/functional/FunctionalExpressionTest.java)
        * Запускать c аргументом `hard` или `easy`;
 * *Mini* (для тестирования)
    * Не поддерживаются бинарные операции
    * Код находится в файле [functionalMiniExpression.js](/hw05-JS-FunctionalExpression/functionalMiniExpression.js).
    * [Исходный код тестов](javascript/jstest/functional/FunctionalMiniTest.java)
        * Запускать c аргументом `hard` или `easy`;
 * *OneFP* (38, 39). Дополнительно реализовать поддержку:
    * переменных: `y`, `z`;
    * констант:
        * `one` – 1;
        * `two` – 2;
    * операций:
        * `*+` (`madd`) – тернарный оператор произведение-сумма, `2 3 4 *+` равно 10;
        * `_` (`floor`) – округление вниз `2.7 _` равно 2;
        * `^` (`ceil`) – округление вверх `2.7 ^` равно 3.
    * [Исходный код тестов](javascript/jstest/functional/FunctionalOneFPTest.java)

Запуск тестов:
 * Для запуска тестов используется [GraalJS](https://github.com/graalvm/graaljs)
   (часть проекта [GraalVM](https://www.graalvm.org/), вам не требуется их скачивать отдельно)
 * Для запуска тестов можно использовать скрипты [TestJS.cmd](javascript/TestJS.cmd) и [TestJS.sh](javascript/TestJS.sh)
    * Репозиторий должен быть скачан целиком.
    * Скрипты должны находиться в каталоге `javascript` (их нельзя перемещать, но можно вызывать из других каталогов).
    * В качестве аргументов командной строки указывается полное имя класса теста и модификация, 
      например `jstest.functional.FunctionalExpressionTest hard`.
 * Для самостоятельно запуска из консоли необходимо использовать командную строку вида:
    `java -ea --module-path=<js>/graal --class-path <js> jstest.functional.FunctionalExpressionTest {hard|easy}`, где
    * `-ea` – включение проверок времени исполнения;
    * `--module-path=<js>/graal` путь к модулям Graal (здесь и далее `<js>` путь к каталогу `javascript` этого репозитория);
    * `--class-path <js>` путь к откомпилированным тестам;
    * {`hard`|`easy`} указание тестируемой модификации.

## Домашнее задание 6. Объектные выражения на JavaScript

> Реализация: [objectExpression.js](/hw06%2B07-JS-ObjectExpression%2BExceptions/objectExpression.js)

1. Разработайте классы `Const`, `Variable`, `Add`, `Subtract`, `Multiply`, `Divide`, `Negate` для представления выражений с одной переменной.
   * Пример описания выражения 2x-3:

      ```
      let expr = new Subtract(
          new Multiply(
              new Const(2),
              new Variable("x")
          ),
          new Const(3)
      );

      println(expr.evaluate(5));
      ```

   * При вычислении такого выражения вместо каждой переменной подставляется её значение, переданное в качестве аргумента метода `evaluate`. Таким образом, результатом вычисления приведенного примера должно стать число 7.
   * Метод `toString()` должен выдавать запись выражения в [обратной польской записи](https://ru.wikipedia.org/wiki/Обратная_польская_запись). Например, `expr.toString()` должен выдавать «2 x * 3 -».

2. _Сложный вариант_.
   * Метод `diff("x")` должен возвращать выражение, представляющее производную исходного выражения по переменной x. Например, `expr.diff("x")` должен возвращать выражение, эквивалентное `new Const(2)`, выражения `new Subtract(new Const(2), new Const(0))` и

      ```
      new Subtract(
          new Add(
              new Multiply(new Const(0), new Variable("x")),
              new Multiply(new Const(2), new Const(1))
          )
          new Const(0)
      )
      ```
                 
      так же будут считаться правильным ответом).
   * Функция `parse` должна выдавать разобранное объектное выражение.

3. При выполнение задания следует обратить внимание на:
   * Применение инкапсуляции.
   * Выделение общего кода для операций.
   * Минимизацию необходимой памяти.

Модификации:
 * *Базовая*
    * [Исходный код тестов](javascript/jstest/object/ObjectExpressionTest.java)
        * Запускать c аргументом `easy`, `hard` или `bonus`.
 * *Harmonic* (38, 39). Дополнительно реализовать поддержку:
    * функций от двух аргументов:
        * `Hypot` (`hypot`) – квадрат гипотенузы, `3 4 hypot` равно 25;
        * `HMean` (`hmean`) – гармоническое среднее, `5 20 hmean` равно 8;
    * [Исходный код тестов](javascript/jstest/object/ObjectHarmonicTest.java)

## Домашнее задание 7. Обработка ошибок на JavaScript

> Реализация: [objectExpression.js](/hw06%2B07-JS-ObjectExpression%2BExceptions/objectExpression.js)

1. Добавьте в предыдущее домашнее задание функцию `parsePrefix(string)`, разбирающую выражения, задаваемые записью вида «(- (* 2 x) 3)». Если разбираемое выражение некорректно, метод parsePrefix должен бросать человеко-читаемое сообщение об ошибке.  
2. Добавьте в предыдущее домашнее задание метод `prefix()`, выдающий выражение в формате, ожидаемом функцией parsePrefix.  
3. При выполнение задания следует обратить внимание на:
   * Применение инкапсуляции.
   * Выделение общего кода для операций.
   * Минимизацию необходимой памяти.
   * Обработку ошибок.

Модификации:
 * *Базовая*
    * [Исходный код тестов](javascript/jstest/prefix/PrefixParserTest.java)
        * Запускать c аргументом `easy` или `hard`
 * *PostfixMeans* (38-39). Дополнительно реализовать поддержку:
    * выражений в постфиксной записи: `(2 3 +)` равно 5
    * операций произвольного числа аргументов:
        * `ArithMean` (`arith-mean`) – арифметическое среднее `(arith-mean 1 2 6)` равно 3;
        * `GeomMean` (`geom-mean`) – геометрическое среднее `(geom-mean 1 2 4)` равно 2;
        * `HarmMean` (`harm-mean`) – гармоническое среднее, `(harm-mean 2 3 6)` равно 3;
    * [Исходный код тестов](javascript/jstest/prefix/PostfixMeansTest.java)

