package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.User;
import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.UserColor;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", UserColor.BLUE),
            new User(6, "pashka", "Pavel Mavrin", UserColor.BLUE),
            new User(8, "Emgariko", "Emil Garipov", UserColor.RED),
            new User(9, "geranazarov555", "Georgiy Nazarov", UserColor.BLUE),
            new User(11, "tourist", "Gennady Korotkevich", UserColor.GREEN)
    );
    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, 11, "touristream 012: Codeforces Round 672 (Div. 2)",
                    "Tune in to my Twitch to watch me solve today's Codeforces Round 672 (Div. 2) virtually.\n" +
                    "\n" +
                    "UPD: Video on YouTube."),
            new Post(2, 6, "ITMO Algorithms Course", "Hello Codeforces!\n" +
                    "\n" +
                    "I teach a course on algorithms and data structures at ITMO University. During the last year I was streaming all my lectures on Twitch and uploaded the videos on Youtube.\n" +
                    "\n" +
                    "This year I want to try to do it in English.\n" +
                    "\n" +
                    "This is a four-semester course. The rough plan for the first semester:\n" +
                    "\n" +
                    "Algorithms, complexity, asymptotics\n" +
                    "Sorting algorithms\n" +
                    "Binary heap\n" +
                    "Binary search\n" +
                    "Linked lists, Stack, Queue\n" +
                    "Amortized analysis\n" +
                    "Fibonacci Heap\n" +
                    "Disjoint Set Union\n" +
                    "Dynamic Programming\n" +
                    "Hash Tables\n" +
                    "The lectures are open for everybody. If you want to attend, please fill out this form to help me pick the optimal day and time.\n" +
                    "\n" +
                    "See you!\n" +
                    "\n"),
            new Post(3, 8, "C++ Templates", "-Шаблон:\n" +
                    "template <typename T>\n" +
                    "struct vector {\n" +
                    "    vector() {\n" +
                    "        std::cout << \"T\";\n" +
                    "    }\n" +
                    "private:\n" +
                    "    size_t size_;\n" +
                    "    size_t capacity;\n" +
                    "};\n" +
                    "-Специализации: \n" +
                    "template<> \n" +
                    "struct vector<bool> {\n" +
                    "\n" +
                    "};\n" +
                    "-Partial-специализация:\n" +
                    "template <typename V>\n" +
                    "struct vector<V*> {\n" +
                    "    vector() {\n" +
                    "        std::cout << \"T*\";\n" +
                    "    }\n" +
                    "private:\n" +
                    "    size_t size_;\n" +
                    "    size_t capacity;\n" +
                    "};\n" +
                    "-Partial-специализации для функций запрещены\n" +
                    "-Так как у функций есть и перегрузка и специализация, снача выбирается перегрузка, затем специализация\n" +
                    "-Если есть две перегрузки функции: одна шаблонная(идеально удовлетворила deduction):\n" +
                    "template<typename T>\n" +
                    "void foo(*T):\n" +
                    ", вторая(тип ее аргументов совпадает с тем, который определил deduction у первой):\n" +
                    "void foo(int*)\n" +
                    " при вызове:\n" +
                    "int* p;\n" +
                    "foo(p);\n" +
                    "приоритет отдается второй функции.\n" +
                    "-Так же всегда побеждают explicit-специализации"),
            new Post(4, 1, "Educational Codeforces Round 91 is ruined and unrated", "Hello.\n" +
                    "\n" +
                    "Unfortunately, the Educational Codeforces Round 91 round will be unrated and, actually, it was completely ruined. I don’t understand what happened yet. At some point in time, all systems began to work unstably and actually stopped working. So far I have no understanding what happened. You may have to change the schedule for future rounds. Sorry. We have such a black stripe. Very upset and demotivated.\n" +
                    "\n" +
                    "Mike."),
            new Post(5, 1, "Что же такое Codeforces?",
                    "Я еще достаточно давно заметил, что все сайты по теме соревнований по программированию работают преимущественно по принципам Web 1.0. Тем временем уже наступил 21-й век, прошел 30-й чемпионат мира по программированию, а Google отметил свое 10-летие. Непорядок! В то время когда Software-as-a-Service завоевывает мир,  организаторы контестов все еще копируют по сети тесты в недра тестирующих систем. Непорядок!")
    );
    private static final List<UserColor> userColors = Arrays.asList(
            UserColor.GREEN, UserColor.BLUE, UserColor.RED
    );
    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);
        data.put("userColors", userColors);
        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
