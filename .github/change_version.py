# coding: utf-8

import argparse

if __name__ == "__main__":
    # from jproperties import Properties

    parser = argparse.ArgumentParser()
    parser.add_argument('version', help="New version", type=str)
    args = parser.parse_args()

    with open('./gradle.properties', 'r+') as file:
        old_text = ""
        new_text = ""
        new_version: str = args.version
        new_version = new_version.replace('"', '')
        new_version = new_version[1:]
        old_version: str = ""

        # Bad
        for line in file.readlines():
            start = line.find('mod_version')

            if start < 0:
                old_text += line
                continue

            old_version = line[start + 12:-1]
            print(f'Old version: {old_version}, New version: {new_version}')
            # file.writelines(f'mod_version={new_version}\n')
            old_text += line

        new_text = old_text.replace(old_version, new_version)

        file.seek(0)
        file.truncate(0)
        file.write(new_text)
        file.close()

        # configs = Properties()
        # configs.load(file)
        # 
        # new_version: str = args.version
        # new_version.replace('"', '')
        # 
        # configs['mod_version'] = new_version[1:]
        # 
        # print(f'New Version: {str(configs['mod_version'])}')
        # 
        # file.seek(0)
        # file.truncate(0)
        # configs.store(file)