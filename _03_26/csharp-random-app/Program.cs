using System.Globalization;

Console.OutputEncoding = System.Text.Encoding.UTF8;

while (true)
{
    Console.WriteLine("\n=== 随机数应用程序（C#）===");
    Console.WriteLine("1. 抢红包（随机拆分金额）");
    Console.WriteLine("2. 盲盒抽取（按稀有度概率）");
    Console.WriteLine("3. 随机点名");
    Console.WriteLine("4. 抽奖（自定义奖项）");
    Console.WriteLine("5. 轮盘赌（模拟）");
    Console.WriteLine("0. 退出");

    int choice = ReadInt("请选择功能：", 0, 5);
    Console.WriteLine();

    switch (choice)
    {
        case 1:
            RedPacketDemo();
            break;
        case 2:
            BlindBoxDemo();
            break;
        case 3:
            RandomRollCallDemo();
            break;
        case 4:
            LotteryDemo();
            break;
        case 5:
            RouletteDemo();
            break;
        case 0:
            Console.WriteLine("程序结束，再见！");
            return;
    }
}

static void RedPacketDemo()
{
    Console.WriteLine("【抢红包】");
    decimal totalAmount = ReadDecimal("请输入总金额（元，例如 20.88）：", 0.01m, 100000m);
    int people = ReadInt("请输入人数：", 1, 1000);

    int totalCents = (int)Math.Round(totalAmount * 100m);
    if (totalCents < people)
    {
        Console.WriteLine("总金额太小，无法保证每人至少 0.01 元。");
        return;
    }

    List<int> cuts = new() { 0, totalCents };
    for (int i = 0; i < people - 1; i++)
    {
        cuts.Add(Random.Shared.Next(1, totalCents));
    }
    cuts.Sort();

    Console.WriteLine("\n红包分配结果：");
    for (int i = 1; i < cuts.Count; i++)
    {
        int cents = cuts[i] - cuts[i - 1];
        Console.WriteLine($"第{i}个人：{cents / 100m:F2} 元");
    }
}

static void BlindBoxDemo()
{
    Console.WriteLine("【盲盒抽取】");
    Console.WriteLine("稀有度概率：普通(70%)、稀有(20%)、传说(10%)");
    int count = ReadInt("请输入抽取次数：", 1, 100);

    Dictionary<string, string[]> pool = new()
    {
        ["普通"] = ["贴纸", "钥匙扣", "卡片", "小徽章"],
        ["稀有"] = ["金属徽章", "限定挂件", "荧光手环"],
        ["传说"] = ["隐藏款手办", "签名海报"]
    };

    Console.WriteLine("\n抽取结果：");
    for (int i = 1; i <= count; i++)
    {
        int roll = Random.Shared.Next(1, 101);
        string rarity = roll <= 70 ? "普通" : roll <= 90 ? "稀有" : "传说";
        string item = pool[rarity][Random.Shared.Next(pool[rarity].Length)];
        Console.WriteLine($"第{i}抽 -> {rarity}：{item}");
    }
}

static void RandomRollCallDemo()
{
    Console.WriteLine("【随机点名】");
    Console.WriteLine("请输入名单（用英文逗号分隔），例如：张三,李四,王五");
    string? line = Console.ReadLine();

    List<string> names = (line ?? "")
        .Split(',', StringSplitOptions.TrimEntries | StringSplitOptions.RemoveEmptyEntries)
        .Distinct(StringComparer.OrdinalIgnoreCase)
        .ToList();

    if (names.Count == 0)
    {
        Console.WriteLine("名单为空，无法点名。");
        return;
    }

    int count = ReadInt($"点名几人（1~{names.Count}）：", 1, names.Count);
    Shuffle(names);

    Console.WriteLine("\n点名结果：");
    for (int i = 0; i < count; i++)
    {
        Console.WriteLine($"{i + 1}. {names[i]}");
    }
}

static void LotteryDemo()
{
    Console.WriteLine("【抽奖】");
    Console.WriteLine("请依次输入奖项名称（输入空行结束）：");

    List<string> prizes = new();
    while (true)
    {
        Console.Write($"奖项 {prizes.Count + 1}: ");
        string? prize = Console.ReadLine()?.Trim();
        if (string.IsNullOrEmpty(prize))
        {
            break;
        }
        prizes.Add(prize);
    }

    if (prizes.Count == 0)
    {
        Console.WriteLine("没有奖项，无法抽奖。");
        return;
    }

    int winners = ReadInt($"抽几个中奖名额（1~{prizes.Count}）：", 1, prizes.Count);
    Shuffle(prizes);

    Console.WriteLine("\n中奖结果：");
    for (int i = 0; i < winners; i++)
    {
        Console.WriteLine($"中奖{i + 1}：{prizes[i]}");
    }
}

static void RouletteDemo()
{
    Console.WriteLine("【轮盘赌模拟】");
    Console.WriteLine("可选押注：红 / 黑 / 绿（绿赔率更高）");

    string bet;
    while (true)
    {
        Console.Write("请输入押注颜色：");
        bet = (Console.ReadLine() ?? "").Trim();
        if (bet is "红" or "黑" or "绿")
        {
            break;
        }
        Console.WriteLine("输入无效，请输入 红、黑 或 绿。");
    }

    string result = SpinRoulette();
    Console.WriteLine($"轮盘结果：{result}");

    if (bet == result)
    {
        int multiplier = result == "绿" ? 14 : 2;
        Console.WriteLine($"恭喜你押中了！赔率 x{multiplier}");
    }
    else
    {
        Console.WriteLine("很遗憾，未押中。");
    }
}

static string SpinRoulette()
{
    // 近似欧洲轮盘：红18格，黑18格，绿1格
    int n = Random.Shared.Next(1, 38); // 1~37
    if (n == 37) return "绿";
    return n % 2 == 0 ? "红" : "黑";
}

static int ReadInt(string prompt, int min, int max)
{
    while (true)
    {
        Console.Write(prompt);
        if (int.TryParse(Console.ReadLine(), out int value) && value >= min && value <= max)
        {
            return value;
        }
        Console.WriteLine($"请输入 {min} 到 {max} 之间的整数。");
    }
}

static decimal ReadDecimal(string prompt, decimal min, decimal max)
{
    while (true)
    {
        Console.Write(prompt);
        string text = (Console.ReadLine() ?? "").Trim();

        if (decimal.TryParse(text, NumberStyles.Number, CultureInfo.InvariantCulture, out decimal v1) && v1 >= min && v1 <= max)
        {
            return v1;
        }
        if (decimal.TryParse(text, NumberStyles.Number, CultureInfo.CurrentCulture, out decimal v2) && v2 >= min && v2 <= max)
        {
            return v2;
        }

        Console.WriteLine($"请输入 {min} 到 {max} 之间的数字。");
    }
}

static void Shuffle<T>(IList<T> list)
{
    for (int i = list.Count - 1; i > 0; i--)
    {
        int j = Random.Shared.Next(i + 1);
        (list[i], list[j]) = (list[j], list[i]);
    }
}
