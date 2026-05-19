Add-Type -AssemblyName System.Drawing

$inputPath = (Get-ChildItem 'C:\Users\Administrator\Pictures\Saved Pictures\*_20260506210838_32_35.png' | Select-Object -First 1).FullName
$outputPath = 'D:\GitHub\JavaEE\_0320\edited_jump_rope_20260516.png'

$bitmap = [System.Drawing.Bitmap]::FromFile($inputPath)
$graphics = [System.Drawing.Graphics]::FromImage($bitmap)
$graphics.SmoothingMode = [System.Drawing.Drawing2D.SmoothingMode]::AntiAlias
$graphics.InterpolationMode = [System.Drawing.Drawing2D.InterpolationMode]::HighQualityBicubic
$graphics.PixelOffsetMode = [System.Drawing.Drawing2D.PixelOffsetMode]::HighQuality
$graphics.TextRenderingHint = [System.Drawing.Text.TextRenderingHint]::AntiAliasGridFit

function Copy-Region {
    param(
        [System.Drawing.Bitmap]$Image,
        [System.Drawing.Graphics]$Graphics,
        [int]$SourceX,
        [int]$SourceY,
        [int]$SourceWidth,
        [int]$SourceHeight,
        [int]$DestX,
        [int]$DestY,
        [int]$DestWidth,
        [int]$DestHeight
    )

    $srcRect = [System.Drawing.Rectangle]::new($SourceX, $SourceY, $SourceWidth, $SourceHeight)
    $dstRect = [System.Drawing.Rectangle]::new($DestX, $DestY, $DestWidth, $DestHeight)
    $Graphics.DrawImage($Image, $dstRect, $srcRect, [System.Drawing.GraphicsUnit]::Pixel)
}

function Fill-Rect {
    param(
        [System.Drawing.Graphics]$Graphics,
        [int]$X,
        [int]$Y,
        [int]$Width,
        [int]$Height,
        [System.Drawing.Color]$Color
    )

    $brush = [System.Drawing.SolidBrush]::new($Color)
    $Graphics.FillRectangle($brush, $X, $Y, $Width, $Height)
    $brush.Dispose()
}

function Draw-Text {
    param(
        [System.Drawing.Graphics]$Graphics,
        [string]$Text,
        [string]$Family,
        [float]$Size,
        [System.Drawing.FontStyle]$Style,
        [System.Drawing.Color]$Color,
        [float]$X,
        [float]$Y
    )

    $font = [System.Drawing.Font]::new($Family, $Size, $Style, [System.Drawing.GraphicsUnit]::Pixel)
    $brush = [System.Drawing.SolidBrush]::new($Color)
    $Graphics.DrawString($Text, $font, $brush, $X, $Y)
    $brush.Dispose()
    $font.Dispose()
}

function Measure-Text {
    param(
        [System.Drawing.Graphics]$Graphics,
        [string]$Text,
        [string]$Family,
        [float]$Size,
        [System.Drawing.FontStyle]$Style
    )

    $font = [System.Drawing.Font]::new($Family, $Size, $Style, [System.Drawing.GraphicsUnit]::Pixel)
    $measured = $Graphics.MeasureString($Text, $font)
    $font.Dispose()
    return $measured
}

$charGe = [string][char]0x4E2A
$pmText = ([string][char]0x4E0B) + [char]0x5348
$goalCompletion = ([string][char]0x76EE) + [char]0x6807 + [char]0x5B8C + [char]0x6210 + [char]0x5EA6 + '105%'

$black = [System.Drawing.Color]::FromArgb(255, 16, 16, 16)
$gray = [System.Drawing.Color]::FromArgb(255, 112, 112, 112)
$white = [System.Drawing.Color]::FromArgb(255, 255, 255, 255)
$orangeTop = [System.Drawing.Color]::FromArgb(255, 253, 97, 42)

Fill-Rect -Graphics $graphics -X 34 -Y 28 -Width 120 -Height 54 -Color $orangeTop
Draw-Text -Graphics $graphics -Text '23:11' -Family 'Microsoft YaHei' -Size 31 -Style Bold -Color $black -X 49 -Y 34

Copy-Region -Image $bitmap -Graphics $graphics -SourceX 500 -SourceY 292 -SourceWidth 270 -SourceHeight 82 -DestX 38 -DestY 289 -DestWidth 430 -DestHeight 82
Draw-Text -Graphics $graphics -Text ("2026/05/16 " + $pmText + '9:31') -Family 'Microsoft YaHei' -Size 42 -Style Bold -Color $white -X 49 -Y 299

Fill-Rect -Graphics $graphics -X 48 -Y 485 -Width 250 -Height 110 -Color $white
$mainCountSize = Measure-Text -Graphics $graphics -Text '1262' -Family 'Microsoft YaHei' -Size 76 -Style Bold
Draw-Text -Graphics $graphics -Text '1262' -Family 'Microsoft YaHei' -Size 76 -Style Bold -Color $black -X 56 -Y 494
Draw-Text -Graphics $graphics -Text $charGe -Family 'Microsoft YaHei' -Size 40 -Style Bold -Color $black -X (56 + $mainCountSize.Width - 2) -Y 528

Fill-Rect -Graphics $graphics -X 54 -Y 694 -Width 206 -Height 70 -Color $white
Draw-Text -Graphics $graphics -Text '00:21:58' -Family 'Microsoft YaHei' -Size 42 -Style Bold -Color $black -X 58 -Y 701

Fill-Rect -Graphics $graphics -X 319 -Y 694 -Width 156 -Height 70 -Color $white
$activeSize = Measure-Text -Graphics $graphics -Text '171' -Family 'Microsoft YaHei' -Size 41 -Style Bold
Draw-Text -Graphics $graphics -Text '171' -Family 'Microsoft YaHei' -Size 41 -Style Bold -Color $black -X 324 -Y 703
Draw-Text -Graphics $graphics -Text 'kcal' -Family 'Microsoft YaHei' -Size 25 -Style Bold -Color $gray -X (324 + $activeSize.Width - 4) -Y 712

Fill-Rect -Graphics $graphics -X 555 -Y 694 -Width 160 -Height 70 -Color $white
$totalSize = Measure-Text -Graphics $graphics -Text '205' -Family 'Microsoft YaHei' -Size 41 -Style Bold
Draw-Text -Graphics $graphics -Text '205' -Family 'Microsoft YaHei' -Size 41 -Style Bold -Color $black -X 559 -Y 703
Draw-Text -Graphics $graphics -Text 'kcal' -Family 'Microsoft YaHei' -Size 25 -Style Bold -Color $gray -X (559 + $totalSize.Width - 4) -Y 712

Fill-Rect -Graphics $graphics -X 320 -Y 858 -Width 100 -Height 68 -Color $white
Draw-Text -Graphics $graphics -Text '23' -Family 'Microsoft YaHei' -Size 41 -Style Bold -Color $black -X 325 -Y 866

Fill-Rect -Graphics $graphics -X 54 -Y 1180 -Width 360 -Height 62 -Color $white
Draw-Text -Graphics $graphics -Text $goalCompletion -Family 'Microsoft YaHei' -Size 38 -Style Bold -Color $black -X 60 -Y 1187

Fill-Rect -Graphics $graphics -X 76 -Y 1388 -Width 212 -Height 82 -Color $white
$goalSize = Measure-Text -Graphics $graphics -Text '1200' -Family 'Microsoft YaHei' -Size 55 -Style Bold
Draw-Text -Graphics $graphics -Text '1200' -Family 'Microsoft YaHei' -Size 55 -Style Bold -Color $black -X 81 -Y 1396
Draw-Text -Graphics $graphics -Text $charGe -Family 'Microsoft YaHei' -Size 29 -Style Bold -Color $black -X (81 + $goalSize.Width + 2) -Y 1420

Fill-Rect -Graphics $graphics -X 492 -Y 1388 -Width 212 -Height 82 -Color $white
$actualSize = Measure-Text -Graphics $graphics -Text '1262' -Family 'Microsoft YaHei' -Size 55 -Style Bold
Draw-Text -Graphics $graphics -Text '1262' -Family 'Microsoft YaHei' -Size 55 -Style Bold -Color $black -X 497 -Y 1396
Draw-Text -Graphics $graphics -Text $charGe -Family 'Microsoft YaHei' -Size 29 -Style Bold -Color $black -X (497 + $actualSize.Width + 2) -Y 1420

Fill-Rect -Graphics $graphics -X 345 -Y 1769 -Width 154 -Height 62 -Color $white
Draw-Text -Graphics $graphics -Text '00:21:58' -Family 'Microsoft YaHei' -Size 36 -Style Bold -Color $black -X 347 -Y 1776

Fill-Rect -Graphics $graphics -X 534 -Y 1769 -Width 118 -Height 62 -Color $white
Draw-Text -Graphics $graphics -Text '1262' -Family 'Microsoft YaHei' -Size 36 -Style Bold -Color $black -X 538 -Y 1776

Fill-Rect -Graphics $graphics -X 697 -Y 1769 -Width 84 -Height 62 -Color $white
Draw-Text -Graphics $graphics -Text '57' -Family 'Microsoft YaHei' -Size 36 -Style Bold -Color $black -X 703 -Y 1776

$bitmap.Save($outputPath, [System.Drawing.Imaging.ImageFormat]::Png)

$graphics.Dispose()
$bitmap.Dispose()

Write-Output $outputPath
